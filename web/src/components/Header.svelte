<script>
	import Fa from "svelte-fa";
	import {
		faHeart,
		faEnvelope,
		faShareNodes,
		faUser,
		faShoppingCart,
	} from "@fortawesome/free-solid-svg-icons";
	import SearchBar from "./SearchBar.svelte";
</script>

<header class="p-3 d-flex flex-row align-items-center">
	<h1 class="text-white">Best Music</h1>

	<div class="container">
		<nav class="row justify-content-center align-items-center">
			<div class="col-4">
				<SearchBar />
			</div>
			<ul class="icons col d-flex justify-content-end text-white gap-4 list-type-none">
				<li><Fa icon={faHeart} size="2x" /></li>
				<li><Fa icon={faEnvelope} size="2x" /></li>
				<li><Fa icon={faShareNodes} size="2x" /></li>
				<li><Fa icon={faUser} size="2x" /></li>
				<li><Fa icon={faShoppingCart} size="2x" /></li>
			</ul>
		</nav>
	</div>
</header>

<style lang="scss">
	h1 {
		font-family: "Fall In Love";
	}

	ul {
		list-style-type: none;

		& li {
			cursor: pointer;
		}
	}
</style>
