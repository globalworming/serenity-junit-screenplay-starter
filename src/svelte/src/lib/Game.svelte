<script>

    import {onMount} from 'svelte';
    import {tweened} from 'svelte/motion';
    import {cubicOut} from 'svelte/easing';

    let highscore = 0;
    let health = 100;
    let run = true;
    let tiles = [
        {id: 'tile-0', name: 'danger'},
        {id: 'tile-1', name: 'danger'},
        {id: 'tile-2', name: 'danger'}
    ];
    let score = 0;
    let size = tweened(0, {
        duration: 100,
        easing: cubicOut
    });
    let difficulty = 1;

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
            score++;
        }
        if (score > highscore) {
            highscore = score;
        }

    }

    function nextHealth(tiles) {
        if (tiles.filter(tile => tile.name === ('ultradanger')).length === tiles.length) {
            health = Math.max(--health, 0);
        }
        if (health <= 0) {
            run = false;
        }

    }

    const restart = () => {
        score = 0;
        health = 100;
        run = true;
    }

    const nextFrame = () => {
        if (!run) {
            return;
        }
        nextHealth(tiles);
        nextPoints(tiles);
        tiles = nextTiles(tiles);
        size.set($size < 50 ? 100 : 0)
    };

    onMount(() => {
        let counter = 0;
        const tick = setInterval(() => {
            if (counter % (Math.round(100 / difficulty)) === 0) {
                nextFrame();
            }
            ++counter
        }, 10);

        return () => {
            clearInterval(tick);
        };
    });


</script>
<h2>score: {score} - highscore: <span class="see-highscore">{highscore}</span> - health: {health}</h2>
<div>
    <button on:click="{e => --difficulty}">-</button>
    <span>difficulty {difficulty}</span>
    <button on:click="{e => ++difficulty}">+</button>
</div>
{#if !run}
    <button class="do-restart" on:click="{e => restart()}">restart</button>
{/if}

{#if run}
  {#each tiles as { id, name }, i}
      <div class="tile {id} {name}" on:click="{e => tiles[i].name = ''}"></div>
  {/each}
  <div class="bar" style="width: {$size}px"></div>
{/if}


<style>
    .bar {
        height: 3px;
        background: #0d78ae;
    }

    .tile {
        width: 100px;
        height: 100px;
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
