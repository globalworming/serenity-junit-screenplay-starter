<script>

    import {onMount} from 'svelte';
    import {tweened} from 'svelte/motion';
    import {cubicOut} from 'svelte/easing';

    let highscore = 0;
    let health = 10;
    let run = true;
    let tiles = [
        {id: 'tile-0', name: 'danger'},
        {id: 'tile-1', name: 'danger'},
        {id: 'tile-2', name: 'danger'}
    ];
    let score = 0;
    let widthPercentage = tweened(0, {
        duration: 300,
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
        health = Math.max(health - tiles.filter(tile => tile.name === ('ultradanger')).length, 0);
        if (health <= 0) {
            run = false;
        }

    }

    const restart = () => {
        score = 0;
        health = 10;
        run = true;
    }

    const nextFrame = () => {
        if (!run) {
            return;
        }
        nextHealth(tiles);
        nextPoints(tiles);
        tiles = nextTiles(tiles);
        widthPercentage.set($widthPercentage < 50 ? 100 : 0)
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
<h2>score: <span class="see-score">{score}</span> - highscore: <span class="see-highscore">{highscore}</span> -
    health: {health}</h2>
<div>
    <button class="decrease-difficulty" on:click="{e => difficulty = Math.max(1, difficulty - 1)}">-</button>
    <span>difficulty </span><span class="see-difficulty">{difficulty}</span>
    <button class="increase-difficulty" on:click="{e => difficulty = Math.min(5, difficulty + 1)}">+</button>
</div>
{#if !run}
    <button class="do-restart" on:click="{e => restart()}">restart</button>
{/if}

{#if run}
  {#each tiles as { id, name }, i}
      <div class="tile {id} {name}" on:click="{e => tiles[i].name = ''}"></div>
  {/each}
  <div class="bar" style="width: {$widthPercentage}%"></div>
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
