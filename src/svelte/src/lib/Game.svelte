<script>

    import {onMount} from 'svelte';

    let tiles = [
        {id: 'tile-0', name: 'danger'},
        {id: 'tile-1', name: ''},
        {id: 'tile-2', name: ''}
    ];
    let points = 0;

    function nextTiles(tiles) {
        tiles.forEach(tile => {
            if (tile.name === "danger") {
                tile.name = "ultradanger"
            }
            if (tile.name === "") {
                tile.name = "danger"
            }
        });
        return tiles;
    }

    function nextPoints(tiles) {
        console.log(tiles.filter(tile => tile.name === ''))
        if (tiles.filter(tile => tile.name === '').length === tiles.length) {
            points += 1
        }
    }

    onMount(() => {
        const interval = setInterval(() => {
            nextPoints(tiles)
            tiles = nextTiles(tiles);
        }, 1000);

        return () => {
            clearInterval(interval);
        };
    });

</script>

<h2>POINTS: {points}</h2>
{#each tiles as { id, name }, i}
    <div class="tile {id} {name}" on:click="{e => tiles[i].name = ''}">

    </div>
{/each}

<style>
    .tile {
        width: 30px;
        height: 30px;
        color: #f5f5f5;
        text-align: center;
        background: #0c0d0e;
    }

    .danger {
        background: #0d78ae;
    }

    .ultradanger {
        background: #ae5389;
    }

</style>
