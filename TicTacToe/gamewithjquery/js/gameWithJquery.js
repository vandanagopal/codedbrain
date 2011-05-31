$(document).ready(function() {
    init();
});


function init() {
    locationsOfAllCreatures = new Array();
    movements= GetArrayOfMovementFunctions();
	creatureHtmlIds={snake1:"snakey1",snake2:"snakey2",human:"human",rat:"mouseCage1"};
	creatureIds={snake1:"snakey1",snake2:"snakey2",human:"human",rat:"mouse1"};
    board = {numberOfCells : 36,numberOfColumns : 6,
        isOutOfBounds : function (row, column) {
                         if(row<0 || column<0 || row>5 || column>5) return true;
                         return false;
                    }};
    snake1=new Snake(30,creatureHtmlIds.snake1,1000);
    snake2=new Snake(5,creatureHtmlIds.snake2, 500);
    mouse1= new Rat(10,creatureHtmlIds.rat,"" ,500);
    human=new Human();
    snake1.InitSnake(new Array(creatureIds.snake2));
    snake2.InitSnake(new Array(creatureIds.snake1));

	mouse1.InitRat();
    human.InitHuman(new Array(creatureIds.rat));
   
}

function GetArrayOfMovementFunctions()
{
    return [function(target){
        var up = function(){return target.ratLocationId - board.numberOfColumns;};
        return _do(row(target.ratLocationId) - 1, column(target.ratLocationId), target,up);
    },
        function(target){
            var right = function(){return target.ratLocationId +1;};
            return _do(row(target.ratLocationId), column(target.ratLocationId) + 1, target,right);
        }, function(target){
            var down = function(){return target.ratLocationId + board.numberOfColumns;};
            return _do(row(target.ratLocationId)+1, column(target.ratLocationId), target,down);
        }, function(target){
            var left = function(){return target.ratLocationId -1;};
            return _do(row(target.ratLocationId), column(target.ratLocationId) - 1, target,left);
        }];
}

function _do(row, col, target, movement){
    var newPosition = movement();
    if(!board.isOutOfBounds(row, col) &&  target.ratLocationId != farthest(target.ratLocationId,newPosition, snake2.snakeLocationId)){
        target.MoveRatToId(newPosition);
        return true;
    }
    return false;
}

function row(position){
    return Math.floor(position/board.numberOfColumns);
}

function column(position){
    return position % board.numberOfColumns;
}

function farthest(locationId, anotherLocationId, referenceLocationId)
  {
      if(hops(locationId, referenceLocationId) >  hops(anotherLocationId, referenceLocationId)){
         return locationId;

      }
      return anotherLocationId;
  }
function nearest(locationId, anotherLocationId, referenceLocationId)
{
    if(hops(locationId, referenceLocationId) >  hops(anotherLocationId, referenceLocationId)){
       return anotherLocationId;

    }
    return locationId;
}

  function hops(locationId, referenceLocationId){

      var locationIdColumnNumber= locationId%board.numberOfColumns;
      var locationIdRowNumber= locationId/board.numberOfColumns;
       var referenceLocationIdColumnNumber= referenceLocationId%board.numberOfColumns;
      var referenceLocationIdRowNumber= referenceLocationId/board.numberOfColumns;
      return Math.abs(referenceLocationIdColumnNumber-locationIdColumnNumber) + Math.abs(referenceLocationIdRowNumber-locationIdRowNumber);


  }

function InitHuman(forbiddenLocationCreatureKeys)
{
    this.ForbiddenLocationCreatureKeys = forbiddenLocationCreatureKeys;
    $("td").mouseover(MoveHuman);
    var human=document.getElementById(this.humanHtmlId);
    human.style.position = 'absolute';
    human.style.top = document.getElementById(this.humanLocationId).offsetTop +8;
    human.style.left = document.getElementById(this.humanLocationId).offsetLeft + 8;
    $(human).show();
}

function Human()
{
  this.humanLocationId=2;
  this.humanHtmlId=creatureHtmlIds.human;
  this.InitHuman=InitHuman;
  this.IsForbiddenPosition=IsForbiddenPosition;
  this.ForbiddenLocationCreatureKeys;
  this.IsForbiddenPosition=IsForbiddenPosition;
}


function Snake(snakeLocationId, snakeHtmlId, timeInterval)
{
  this.snakeLocationId=snakeLocationId;
  this.snakeHtmlId=snakeHtmlId;
  this.timeInterval=timeInterval;
  this.observer;
  this.SetTimeIntervalForSnakeMovement = SetTimeIntervalForSnakeMovement; 
  this.MoveSnakeToId = MoveSnakeToId;
  this.InitSnake=InitSnake;
  this.moveSnake=moveSnake;
  this.MoveSnakeTowardsHuman=MoveSnakeTowardsHuman;
  this.MoveRowWiseFirst=MoveRowWiseFirst;
  this.MoveSnakeLeftOrRight =MoveSnakeLeftOrRight;
  this.MoveSnakeUpOrDown=MoveSnakeUpOrDown;
  this.MoveColumnWiseFirst=MoveColumnWiseFirst;

  this.ForbiddenLocationCreatureKeys;
  this.IsForbiddenPosition=IsForbiddenPosition;
  
}



function Rat(ratLocationId, ratCageId, ratFreeId,timeInterval)
{
  this.ratLocationId = ratLocationId;
  this.ratHtmlId= ratCageId;
  this.ratFreeId= ratFreeId;
  this.timeInterval=timeInterval;
  this.isRatInCage=true;
  this.InitRat=InitRat;
  this.SetTimeIntervalToActivateRat = SetTimeIntervalToActivateRat;
  this.MoveRatToId=MoveRatToId;
  this.showRat=showRat;
  this.FreeRatFromCage=FreeRatFromCage;
  this.DistractSnake=DistractSnake;
}


function InitRat()
{
   this.SetTimeIntervalToActivateRat();
   
}

function SetTimeIntervalToActivateRat()
{
  var rat=this;
  setTimeout(function(){rat.showRat();},3000);
}

function showRat()
{
  this.MoveRatToId(this.ratLocationId);
  var id=this.ratHtmlId;
  $("#"+id).fadeIn(5000);
  var rat=this;
  $("#"+this.ratHtmlId).click(function(){rat.FreeRatFromCage();});
 
 }

 function FreeRatFromCage()
 {
   $("#"+this.ratHtmlId).css('display','none');
   this.ratHtmlId="freeMouse";
   this.MoveRatToId(this.ratLocationId);
   $("#"+this.ratHtmlId).show();
   var rat=this;
   setTimeout(rat.DistractSnake,500);
 }


function DistractSnake()
 {
     for(var i=0;i< movements.length;i++)
         if(movements[i](mouse1)) break;

     setTimeout(this.DistractSnake,1000);

 }

function InitSnake(forbiddenLocationCreatureKeys)
{
  this.ForbiddenLocationCreatureKeys = forbiddenLocationCreatureKeys;
  this.MoveSnakeToId(this.snakeLocationId);
$(document.getElementById(this.snakeHtmlId)).show();
//  periodicallyInvoke(moveSnake, this.timeInterval);
    this.SetTimeIntervalForSnakeMovement();
}





function SetTimeIntervalForSnakeMovement() {
    var snake=this;
    snake.timeOutBinding = setTimeout(function(){snake.moveSnake();}, snake.timeInterval);
}

function periodicallyInvoke(func_to_invoke, interval) {
    setTimeout(function(){func_to_invoke(); periodicallyInvoke();}, interval);
}

function moveSnake() {

    this.MoveSnakeTowardsHuman();
    this.SetTimeIntervalForSnakeMovement();

}

function MoveSnakeTowardsHuman() {
  
if(this.snakeLocationId==human.humanLocationId) return;
var shouldMoveRowWiseFirst = Math.floor(Math.random() * 2);
if (shouldMoveRowWiseFirst ) this.MoveRowWiseFirst();
else this.MoveColumnWiseFirst();
     
}

function MoveRowWiseFirst() {

    if (!AreSnakeAndHumanInSameColumn(this)) this.MoveSnakeLeftOrRight();
    else this.MoveSnakeUpOrDown();

}

function AreSnakeAndHumanInSameColumn(snake) {
    if (snake.snakeLocationId % board.numberOfColumns == human.humanLocationId % board.numberOfColumns) return true;
    else return false;
}

function MoveSnakeLeftOrRight() {
    /*Move Snake Left*/
    if (IsCreature1ToRightOfCreature2(this.snakeLocationId,human.humanLocationId)) this.MoveSnakeToId(this.snakeLocationId - 1); 

    else this.MoveSnakeToId(this.snakeLocationId + 1);
}

function IsCreature1ToRightOfCreature2(creature1Id, creature2Id)
{
 if(creature1Id % 6 > creature2Id % board.numberOfColumns)
   return true;
 else return false;
}

function IsCreature1BelowCreature2(creature1Id, creature2Id)
{
 if(creature1Id > creature2Id)
   return true;
 else return false;
}

function MoveSnakeUpOrDown() {
    /* Move Snake up*/
    if (IsCreature1BelowCreature2(this.snakeLocationId,human.humanLocationId)) this.MoveSnakeToId(this.snakeLocationId - board.numberOfColumns);

    else this.MoveSnakeToId(this.snakeLocationId + board.numberOfColumns);
}

function MoveColumnWiseFirst() {

    if (!AreSnakeAndHumanInSameRow(this)) this.MoveSnakeUpOrDown();

    else this.MoveSnakeLeftOrRight();
}

function AreSnakeAndHumanInSameRow(snake) {
    if (Math.floor(snake.snakeLocationId / board.numberOfColumns) == Math.floor(human.humanLocationId / board.numberOfColumns)) return true;
    else return false;
}

function MoveRatToId(id)
{
    this.ratLocationId=id;
    locationsOfAllCreatures[this.ratHtmlId] = id;
     MoveToSameLocationAsObject(document.getElementById(this.ratHtmlId),document.getElementById(id));
}


function MoveSnakeToId(id)
{
    if(this.IsForbiddenPosition(id)) return;
    this.snakeLocationId=id;
    locationsOfAllCreatures[this.snakeHtmlId] = id;
    MoveToSameLocationAsObject(document.getElementById(this.snakeHtmlId),document.getElementById(id));
}

function IsForbiddenPosition(id)
{

  for(var i=0; i< this.ForbiddenLocationCreatureKeys.length;i++)
      if(locationsOfAllCreatures[this.ForbiddenLocationCreatureKeys[i]]==id)
		  return true;
  return false;	
}
function MoveToSameLocationAsObject(objectToBeMoved, targetObject)
{
    objectToBeMoved.style.position = 'absolute';
    objectToBeMoved.style.top = targetObject.offsetTop +8;
    objectToBeMoved.style.left = targetObject.offsetLeft + 8;
}

function MoveHuman(ev) 
{
   if(human.IsForbiddenPosition(this.id)) return;
    MoveToSameLocationAsObject(document.getElementById(human.humanHtmlId),document.getElementById(this.id));
    human.humanLocationId = this.id;

}