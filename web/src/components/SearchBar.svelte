<script>
	import Fa from "svelte-fa";
	import { faSearch } from "@fortawesome/free-solid-svg-icons";
</script>

<div class="search">
	<span>
		<Fa icon={faSearch} color="#626574" />
	</span>
	<input type="text" class="form-control" placeholder="Recherchez un artiste, un titre, ..." />
</div>

<style lang="scss">
	.search {
		position: relative;
	}

	.search {
		& input {
			text-indent: 28px;

			background: #d2c8bf;
			border: 1px solid #d2c8bf;
			border-radius: 2em;

			font-weight: 600;
			color: #3a3c45;

			&:focus {
				box-shadow: none;
				border: 1px solid #c1b3a7;
			}

			&::placeholder {
				color: #787473;
			}
		}

		& span {
			position: absolute;
			top: 50%;
			left: 16px;

			transform: translateY(-50%);
		}
	}
</style>
