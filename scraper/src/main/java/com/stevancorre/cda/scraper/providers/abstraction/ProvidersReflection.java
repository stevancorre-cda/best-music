package com.stevancorre.cda.scraper.providers.abstraction;

import com.google.common.reflect.ClassPath;
import com.stevancorre.cda.scraper.providers.*;

import java.util.ArrayList;

final class ProvidersReflection {
    static final ArrayList<Provider> providers;

    private ProvidersReflection() {
    }

    static {
        providers = new ArrayList<>() {{
            add(new CulturefactoryProvider());
            add(new DiscogsProvider());
            add(new FnacProvider());
            add(new LeboncoinProvider());
            add(new MesVinylesProvider());
            add(new VinylcornerProvider());
        }};

        final String providersPackage = Provider.class.getPackage().getName().replaceAll("\\.abstraction$", "");
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            final ClassPath classpath = ClassPath.from(loader);
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(providersPackage)) {
                final String className = classInfo.getSimpleName();
                if (className.endsWith("_")) continue;

                if (!isCorrectProviderName(className))
                    throw new Error(String.format(
                            "Invalid provider name at %s. Maybe the name should be %s",
                            classInfo.getName(),
                            getCorrectProviderName(className)));

                final Class<?> clazz = Class.forName(classInfo.getName());
                final Object provider = clazz.getDeclaredConstructor().newInstance();
                providers.add((Provider) provider);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isCorrectProviderName(final String name) {
        final boolean isOnlyProvider = !name.equals("Provider");
        final boolean endsWithProvider = name.endsWith("Provider");
        final boolean onlyOneProviderWord = name.split("Provider", -1).length - 1 == 1;

        return isOnlyProvider && endsWithProvider && onlyOneProviderWord;
    }

    private static String getCorrectProviderName(final String invalidName) {
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
