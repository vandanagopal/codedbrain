$(document).ready(function() {
    init();
});

function Rat(locationId, timeInterval)
{
  this.observers=new Array();
  this.locationId = locationId;
  this.ratHtmlId= creatureHtmlIds.rat;
  this.timeInterval=timeInterval;
  this.InitRat=InitRat;
  this.SetTimeIntervalToActivateRat = SetTimeIntervalToActivateRat;
  this.MoveRatToId=MoveRatToId;
  this.showRat=showRat;
  this.FreeRatFromCage=FreeRatFromCage;
  this.DistractSnake=DistractSnake;
    this.isAlive=true;
  this.move = function(newPosition, row, col){
      if(!board.isOutOfBounds(row, col) &&  this.locationId != farthest(this.locationId,newPosition, snake1.locationId)){
          this.MoveRatToId(newPosition);
          return true;
      }
      return false;
  }
  this.Attach=function(observer){
             this.observers.push(observer);
  }
  this.Notify = function(){
      for(var i=0;i<this.observers.length;i++){
          this.observers[i].Notify(this);
      }
  }
  this.die = function(){
       $("#"+rat.ratHtmlId).css('display','none');
  }
}

function init() {
    locationsOfAllCreatures = new Array();
    movements= movementFunctions();
	creatureHtmlIds={snake1:"snakey1",snake2:"snakey2",human:"human",rat:"mouseCage1"};
	creatureIds={snake1:"snakey1",snake2:"snakey2",human:"human",rat:"rat"};
    board = {numberOfCells : 36,numberOfColumns : 6,
        isOutOfBounds : function (row, column) {
                         if(row<0 || column<0 || row>5 || column>5) return true;
                         return false;
                    }};
    snake1=new Snake(30,creatureHtmlIds.snake1,500);
    snake2=new Snake(5,creatureHtmlIds.snake2, 500);
    rat= new Rat(20,200);
    human=new Human();	
    snake1.InitSnake(new Array(creatureIds.snake2));
    snake2.InitSnake(new Array(creatureIds.snake1));
	rat.InitRat();
    rat.Attach(snake1);
    human.InitHuman(new Array(creatureIds.rat));
}


function movementFunctions()
{
    return [function(target){
        var up = function(){return target.locationId - board.numberOfColumns;};
        return _do(row(target.locationId) - 1, column(target.locationId), target,up);
    },
        function(target){
            var right = function(){return target.locationId +1;};
            return _do(row(target.locationId), column(target.locationId) + 1, target,right);
        }, function(target){
            var down = function(){return target.locationId + board.numberOfColumns;};
            return _do(row(target.locationId)+1, column(target.locationId), target,down);
        }, function(target){
            var left = function(){return target.locationId -1;};
            return _do(row(target.locationId), column(target.locationId) - 1, target,left);
        }];
}

function _do(row, col, target, movement){
    var newPosition = movement();
    return target.move(newPosition, row, col);
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
function nearest(currentLocationId, newLocationId, referenceLocationId)
{
    if(hops(currentLocationId, referenceLocationId) > hops(newLocationId, referenceLocationId)){
       return newLocationId;

    }
    return currentLocationId;
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
    var human=$("#"+this.humanHtmlId);
    human.css({'position':'absolute','top': $("#"+this.locationId).offset().top,'left':$("#"+this.locationId).offset().left});
    $(human).show();
}

function Human()
{
  this.locationId=2;
  this.humanHtmlId=creatureHtmlIds.human;
  this.InitHuman=InitHuman;
  this.IsForbiddenPosition=IsForbiddenPosition;
  this.ForbiddenLocationCreatureKeys;
  this.IsForbiddenPosition=IsForbiddenPosition;
  this.die = function(){
       
  }
}


function Snake(snakeLocationId, snakeHtmlId, timeInterval)
{
  this.locationId=snakeLocationId;
  this.snakeHtmlId=snakeHtmlId;
  this.timeInterval=timeInterval;
  this.SetTimeIntervalForSnakeMovement = SetTimeIntervalForSnakeMovement;
  this._assignPositionToSnake = this._assignPositionToSnake;
  this.InitSnake=InitSnake;
  this.moveSnake=moveSnake;
  this.MoveSnakeAfterTarget=MoveSnakeAfterTarget;
  this.ForbiddenLocationCreatureKeys;
  this.IsForbiddenPosition=IsForbiddenPosition;
  this.move = function(newPosition, row, col){
      if(!board.isOutOfBounds(row, col) &&  this.locationId != nearest(this.locationId,newPosition, this.target.locationId)){
          this._assignPositionToSnake(newPosition);
          return true;
      }
      return false;
  };
   this._assignPositionToSnake = function(newPositionId)
    {
        if(this.IsForbiddenPosition(newPositionId)) return;
        this.locationId=newPositionId;
        locationsOfAllCreatures[this.snakeHtmlId] = newPositionId;
        MoveToSameLocationAsObject($("#"+this.snakeHtmlId),$("#"+newPositionId));
    }

    this.Notify = function(target){
        this.target=target;
    }

}

var Creature = function(locationId){
    this.locationId = locationId;
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
  this.MoveRatToId(this.locationId);
  var id=this.ratHtmlId;
  $("#"+id).fadeIn(5000);
  var rat=this;
  $("#"+this.ratHtmlId).click(function(){rat.FreeRatFromCage();});
 
 }

 function FreeRatFromCage()
 {
   $("#"+this.ratHtmlId).css('display','none');
   this.ratHtmlId="freeMouse";
   this.MoveRatToId(this.locationId);
   $("#"+this.ratHtmlId).show();
   this.DistractSnake();
//   setTimeout(rat.DistractSnake,100);
   this.Notify();
 }


function ExecuteMovementInAllDirections(creature) {
    var loopSeed = rand(movements.length);
    for (var i = 0; i < movements.length; i++)
        if (movements[(loopSeed + i) % movements.length](creature)) break;
}
function DistractSnake()
 {
     ExecuteMovementInAllDirections(rat);
     setTimeout(this.DistractSnake,100);

 }

function InitSnake(forbiddenLocationCreatureKeys)
{
  this.ForbiddenLocationCreatureKeys = forbiddenLocationCreatureKeys;
  this._assignPositionToSnake(this.locationId);
  $("#"+this.snakeHtmlId).show();
  this.SetTimeIntervalForSnakeMovement();
  this.target=human;

}

function moveSnake() {

    this.MoveSnakeAfterTarget();
    this.SetTimeIntervalForSnakeMovement();

}


function rand(n) {
    return Math.floor(Math.random() * n);
}
function MoveSnakeAfterTarget(){
     if(this.locationId==this.target.locationId){
         this.target.die();
         this.target = human;
     }
    ExecuteMovementInAllDirections(this);
 }

function SetTimeIntervalForSnakeMovement() {
    var snake=this;
    snake.timeOutBinding = setTimeout(function(){snake.moveSnake();}, snake.timeInterval);
}


function MoveRatToId(id)
{
    this.locationId=id;
    locationsOfAllCreatures[this.ratHtmlId] = id;
     MoveToSameLocationAsObject($("#"+this.ratHtmlId),$("#"+id));
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

 objectToBeMoved.css({'position':'absolute','top':targetObject.offset().top,'left':targetObject.offset().left});

 }

function MoveHuman(ev) 
{
   if(human.IsForbiddenPosition(this.id)) return;
    MoveToSameLocationAsObject($("#"+human.humanHtmlId),$("#"+this.id));
    human.locationId = this.id;

}