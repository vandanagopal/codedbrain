describe("Test", function() {

it("Test the init function and verify all initializations are happening correctly",function(){
loadFixtures('theGame.html');
init();
expect(movements.length).toBe(4);
expect(board.numberOfCells).toBe(36);
expect(board.numberOfColumns).toBe(6);
expect(snake1.target).toBe(human);
expect(snake2.target).toBe(human);
expect(human.ForbiddenLocationCreatureKeys).toEqual(new Array(creatureIds.rat));
expect(snake1.ForbiddenLocationCreatureKeys).toEqual(new Array(creatureIds.snake2));
expect(snake2.ForbiddenLocationCreatureKeys).toEqual(new Array(creatureIds.snake1));
});
})