module.exports = {
	useTabs: true,
	singleQuote: false,
	trailingComma: "all",
	printWidth: 100,
	plugins: ["prettier-plugin-svelte"],
	pluginSearchDirs: ["."],
	overrides: [
		{
			files: "*.svelte",
			options: {
				parser: "svelte",
			},
		},
	],
};
