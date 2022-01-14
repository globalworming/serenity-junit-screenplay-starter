<script context="module">
    export const prerender = true;
</script>
<script>
    import Game from '../lib/Game.svelte';
</script>

<svelte:head>
    <title>Home</title>
</svelte:head>

<section>
    <h1>
        click boxes, get points when all are black
    </h1>
    <Game/>
</section>

<style>
    section {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        flex: 1;
    }

</style>
