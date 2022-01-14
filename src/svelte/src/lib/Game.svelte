<script>

    import {onMount} from 'svelte';
    import {tweened} from 'svelte/motion';
    import {cubicOut} from 'svelte/easing';

    let tiles = [
        {id: 'tile-0', name: 'danger'},
        {id: 'tile-1', name: 'danger'},
        {id: 'tile-2', name: 'danger'}
    ];
    let points = 0;
    let size = tweened(0, {
        duration: 1000,
        easing: cubicOut
    });

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
        if (tiles.filter(tile => tile.name === '').length === tiles.length) {
            points += 1
        }
    }

    onMount(() => {
        const interval = setInterval(() => {
            nextPoints(tiles);
            tiles = nextTiles(tiles);
            size.set($size < 50 ? 100 : 0)
        }, 1000);

        return () => {
            clearInterval(interval);
        };
    });


</script>

<div class="bar" style="width: {$size}px"></div>
<h2>POINTS: {points}</h2>
{#each tiles as { id, name }, i}
    <div class="tile {id} {name}" on:click="{e => tiles[i].name = ''}">

    </div>
{/each}

<style>
    .bar {
        height: 1px;
        background: #0d78ae;
    }

    .tile {
        width: 30px;
        height: 30px;
        color: #f5f5f5;
        text-align: center;
        background: #0c0d0e;
        margin: 1px;
    }

    .danger {
        background: #0d78ae;
    }

    .ultradanger {
        background: #ae5389;
    }

</style>
