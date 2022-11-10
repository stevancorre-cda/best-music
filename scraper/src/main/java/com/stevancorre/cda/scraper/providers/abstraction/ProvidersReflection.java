package com.stevancorre.cda.scraper.providers.abstraction;

import com.google.common.reflect.ClassPath;
import com.stevancorre.cda.scraper.providers.*;

import java.util.ArrayList;

/**
 * Class made to load providers by reflection
 */
final class ProvidersReflection {
    static final ArrayList<Provider> providers;

    private ProvidersReflection() {
    }

    static {
        // for some reason, with module-info, guava can't load classes
        providers = new ArrayList<>() {{
            add(new CulturefactoryProvider());
            add(new DiscogsProvider());
            add(new FnacProvider());
            add(new LeboncoinProvider());
            add(new MesVinylesProvider());
            add(new VinylcornerProvider());
        }};

        // get the package name an prepare the class loader
        final String providersPackage = Provider.class.getPackage().getName().replaceAll("\\.abstraction$", "");
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            // initialize the guava's classpath object
            final ClassPath classpath = ClassPath.from(loader);
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(providersPackage)) {
                // if the name ends with _, we should ignore it
                final String className = classInfo.getSimpleName();
                if (className.endsWith("_")) continue;

                // check if the class name is valid
                if (!isCorrectProviderName(className))
                    throw new Error(String.format(
                            "Invalid provider name at %s. Maybe the name should be %s",
                            classInfo.getName(),
                            getCorrectProviderName(className)));

                // if it is valid, create an instance of this class and add it to the providers list
                final Class<?> clazz = Class.forName(classInfo.getName());
                final Object provider = clazz.getDeclaredConstructor().newInstance();
                providers.add((Provider) provider);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isCorrectProviderName(final String name) {
        // provider name should:
        //   - Not be only "Provider"
        //   - Ends with "Provider"
        //   - Not contain "Provider" more than one time
        final boolean isOnlyProvider = !name.equals("Provider");
        final boolean endsWithProvider = name.endsWith("Provider");
        final boolean onlyOneProviderWord = name.split("Provider", -1).length - 1 == 1;

        return isOnlyProvider && endsWithProvider && onlyOneProviderWord;
    }

    private static String getCorrectProviderName(final String invalidName) {
        // provider name should:
        //   - Not be only "Provider"
        //   - Ends with "Provider"
        //   - Not contain "Provider" more than one time
        if (invalidName.equals("Provider"))
            return "<the website name>Provider";

        if (invalidName.startsWith("Provider"))
            return invalidName.replace("Provider", "") + "Provider";

        if (invalidName.contains("Provider")) {
            int providerIndex = invalidName.indexOf("Provider");
            return invalidName.substring(0, providerIndex) + "Provider";
        }

        return invalidName + "Provider";
    }
}
