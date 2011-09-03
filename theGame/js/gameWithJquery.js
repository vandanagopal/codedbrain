$(document).ready(function() {
    init();
});


function init() {
    locationsOfAllCreatures = new Array();
    movements= movementFunctions();
	creatureHtmlIds={snake1:"snakey1",snake2:"snakey2",human:"human",rat:"mouseCage1",snakeeatingmouse:"snakeeatingmouse"};
	creatureIds={snake1:"snakey1",snake2:"snakey2",human:"human",rat:"rat"};
    board = {numberOfCells : 36,numberOfColumns : 6,
        isOutOfBounds : function (row, column) {
                         if(row<0 || column<0 || row>5 || column>5) return true;
                         return false;
                    }};
    snake1=new Snake(30,creatureHtmlIds.snake1,600,creatureIds.snake1);
    snake2=new Snake(5,creatureHtmlIds.snake2, 650,creatureIds.snake2);
    rat= new Rat(20,300,creatureIds.rat);
    human=new Human(creatureIds.human);
    snake1.InitSnake(new Array(creatureIds.snake2));
    snake2.InitSnake(new Array(creatureIds.snake1));
	rat.InitRat();
    rat.Attach(snake1);
    human.InitHuman(new Array(creatureIds.rat));
	initDiamond();
	 
}


function initDiamond(){
diamond=new Diamond(5,'diamond');
setTimeout(function(){$('#diamond').fadeIn(4000); diamond.visible=true; $('#diamond').mouseover(MoveHuman);moveDiamond();},4000);

  
}

function moveDiamond()
 {

      var position = rand(29);
      moveObject(diamond,position);	  
     if(diamond.visible)
     setTimeout(moveDiamond,3500);

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

  function Diamond(locationId,htmlId){
  this.locationId=locationId;
  this.htmlId=htmlId;
  this.visible=false;
  this.move = function(newPosition, row, col){
      if(!board.isOutOfBounds(row, col)){
          moveObject(this,newPosition);
          return true;
      }
      return false;
  }
 }
  function Rat(locationId, timeInterval,id)
{
  this.observers=new Array();
  this.locationId = locationId;
  this.id=id;
  this.htmlId= creatureHtmlIds.rat;
  this.timeInterval=timeInterval;
  this.InitRat=InitRat;
  this.SetTimeIntervalToActivateRat = SetTimeIntervalToActivateRat;
  this.showRat=showRat;
  this.FreeRatFromCage=FreeRatFromCage;
  this.DistractSnake=DistractSnake;
  this.IsForbiddenPosition=IsForbiddenPosition;
  this.ForbiddenLocationCreatureKeys=new Array();
  this.increaseTimeIntervalBy = 10;
  this.move = function(newPosition, row, col){
      if(!board.isOutOfBounds(row, col) &&  this.locationId != farthest(this.locationId,newPosition, snake1.locationId)){
          MoveCreatureToId(this,newPosition);
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
       $("#"+rat.htmlId).css('display','none');
  }
}


function InitHuman(forbiddenLocationCreatureKeys)
{
    this.ForbiddenLocationCreatureKeys = forbiddenLocationCreatureKeys;
    $("td").mouseover(MoveHuman);
    var human=$("#"+this.htmlId);
    human.css({'position':'absolute','top': $("#"+this.locationId).offset().top,'left':$("#"+this.locationId).offset().left});
    $(human).show();
}

function Human(id)
{
  this.locationId=2;
  this.id=id;
  this.visible=true;
  this.htmlId=creatureHtmlIds.human;
  this.InitHuman=InitHuman;
  this.IsForbiddenPosition=IsForbiddenPosition;
  this.ForbiddenLocationCreatureKeys;
  this.die = function(){
       
  }
}


function Snake(snakeLocationId, snakeHtmlId, timeInterval,id)
{
  this.locationId=snakeLocationId;
  this.htmlId=snakeHtmlId;
  this.id=id;
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
          return this._assignPositionToSnake(newPosition);
      }
      return false;
  };
   this._assignPositionToSnake = function(newPositionId)
    {
        return MoveCreatureToId(this,newPositionId);
    }

    this.Notify = function(target){
        this.target=target;
    }
    this.killTargetAndTakeAppropriateAction = function(){
        this.target.die();
        if(this.target==rat){
            this.toggleHtmlId=this.htmlId;
            changePicForCreature(this,creatureHtmlIds.snakeeatingmouse);
            simulateBloodFall(this);
            this.target=this;
        }
		if(this.target==human){
		  $('#human').effect('explode',{pieces:20},3000,endGameWhenHumanLost);
		  }
    }
    this.AfterBloodSimulationChangeTargetToHuman = function(){
        this.target=human;
        changePicForCreature(this,this.toggleHtmlId);
        this.moveSnake();
    }

}


function AnimateAndCreateBloodDrops(top,left,bloodDropIndex,snake) {


    var blood_drop = $("#blood-drop" + bloodDropIndex);
    blood_drop.css({'position':'absolute','top':top + 30 ,'left':left + 30});
    blood_drop.show();
    var val = 588 - blood_drop.offset().top;
    blood_drop.animate({'top':'+=' + val,opacity:0}, 3000, function() {
                if (bloodDropIndex < 3) {
                    bloodDropIndex++;
                    var image = document.createElement("img");
                    image.src = "../pics/blood-drop.gif";
                    image.id = "blood-drop" + bloodDropIndex;
                    $("#myDiv").append(image);
                    AnimateAndCreateBloodDrops(top, left, bloodDropIndex,snake)
                }
                else {
                    snake.AfterBloodSimulationChangeTargetToHuman();
                }
            });
}
function simulateBloodFall(snake){

    var bloodDropIndex=1;
    var referenceElement = $("#" + (snake.locationId));
    var top = referenceElement.offset().top;
    var left = referenceElement.offset().left;
    AnimateAndCreateBloodDrops(top,left, bloodDropIndex,snake);
}



function InitRat()
{
   this.SetTimeIntervalToActivateRat();
   
}

function SetTimeIntervalToActivateRat()
{
  var rat=this;
  setTimeout(function(){rat.showRat();},this.timeInterval);
}

function showRat()
{
  MoveCreatureToId(this,this.locationId);
  var id=this.htmlId;
  $("#"+id).fadeIn(5000);
  var rat=this;
  $("#"+this.htmlId).click(function(){rat.FreeRatFromCage();});
 
 }

 function FreeRatFromCage()
 {
   changePicForCreature(this,"freeMouse");

   this.DistractSnake();
   this.Notify();
 }

function changePicForCreature(targetObj, toPicId)
{
   $("#"+targetObj.htmlId).css('display','none');
   targetObj.htmlId=toPicId;
   MoveCreatureToId(targetObj,targetObj.locationId);
   $("#"+toPicId).show();
}
function ExecuteMovementInAllDirections(creature) {
    var loopSeed = rand(movements.length);
    for (var i = 0; i < movements.length; i++){
        var index = (loopSeed + i) % movements.length;
        if (movements[index](creature)) break;
    }
}
function DistractSnake()
 {
     ExecuteMovementInAllDirections(rat);

     setTimeout(rat.DistractSnake,rat.timeInterval+=rat.increaseTimeIntervalBy);

 }

function InitSnake(forbiddenLocationCreatureKeys)
{
  this.ForbiddenLocationCreatureKeys = forbiddenLocationCreatureKeys;
  this._assignPositionToSnake(this.locationId);
  $("#"+this.htmlId).show();
  this.SetTimeIntervalForSnakeMovement();
  this.target=human;

}

function moveSnake() {

    this.MoveSnakeAfterTarget();
    if(this.target!=null)
    this.SetTimeIntervalForSnakeMovement();

}


function rand(n) {
    return Math.floor(Math.random() * n);
}

function MoveSnakeAfterTarget(){

    ExecuteMovementInAllDirections(this);
	if(this.locationId==this.target.locationId){
         this.killTargetAndTakeAppropriateAction();
         return;
      }
 }

function SetTimeIntervalForSnakeMovement() {
    var snake=this;
    snake.timeOutBinding = setTimeout(function(){snake.moveSnake();}, snake.timeInterval);
}


function MoveCreatureToId(creature,id)
{
    if(creature.IsForbiddenPosition(id)) return false;
    locationsOfAllCreatures[creature.id] = id;
		return moveObject(creature,id);
}

function moveObject(object,id)
{
    
    object.locationId=id;
    MoveToSameLocationAsObject($("#"+object.htmlId),$("#"+id));
    return true;
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
     if(human.visible==false) return;
     
     MoveCreatureToId(human,this.id);
	  if(hasHumanTouchedDiamond()){
	   $('#diamond').clearQueue();
	   stopSnakes(); 
	   $('#diamond').effect('explode',{pieces:20},3000,allowHumanToGoToNextLevel);
	 }
	
}

function hasHumanTouchedDiamond(){
return human.locationId=='diamond';
}
function allowHumanToGoToNextLevel(){
 
 fadeOutSnakes();
 diamond.visible=false; 
 makeExitDoorVisible();
}

function fadeOutSnakes(){

 
 $('#' + snake1.htmlId).fadeOut(3000);
 $('#' + snake2.htmlId).fadeOut(3000);

}

function stopSnakes(){
snake1.target=null;
 snake2.target=null;
}
function makeExitDoorVisible(){
 MoveToSameLocationAsObject($('#door'),$('#17'));
 $('#door').fadeIn(5000);
 $('#door').mouseover(sendHumanToExit);

}

function sendHumanToExit(){

MoveToSameLocationAsObject($("#exitDoorWithHuman"),$("#door"));
$("#door").css('display','none');
$("#human").css('display','none');
human.visible=false;
$("#exitDoorWithHuman").show();
setTimeout(function(){closeTheDoor();},1000);
alert('Congratulations!!!! You have beaten the coded brain. To play again, refresh the page');
}

function closeTheDoor(){
 MoveToSameLocationAsObject($("#closedDoor"),$("#exitDoorWithHuman"));
 $("#exitDoorWithHuman").css('display','none');
 $("#closedDoor").fadeIn(1000);
 
}

function endGameWhenHumanLost(){
snake1.target=null;
snake2.target=null;
fadeOutSnakes();
diamond.visible=false;
$('#diamond').fadeOut(3000);
$('#'+rat.htmlId).fadeOut(3000);
}