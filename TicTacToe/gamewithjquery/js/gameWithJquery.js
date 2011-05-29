$(document).ready(function() {
    init();
});

function init() {
    locationsOfAllCreatures = new Array();
	creatureHtmlIds={snake1:"snakey1",snake2:"snakey2",human:"human",mouse1:"mouseCage1"};
	creatureIds={snake1:"snakey1",snake2:"snakey2",human:"human",mouse1:"mouse1"};
    board = {numberOfCells : 36,numberOfColumns : 6}
    snake1=new Snake(30,creatureHtmlIds.snake1,1000);
    snake2=new Snake(5,creatureHtmlIds.snake2, 500);
    var mouse1= new Mouse(10,creatureHtmlIds.mouse1,"" ,500);
    human=new Human();
    snake1.InitSnake(new Array(creatureIds.snake2));
    snake2.InitSnake(new Array(creatureIds.snake1));
    /*snake1.AddObserver(snake2);
    snake2.AddObserver(snake1);*/
	mouse1.InitMouse();
    human.InitHuman(new Array(creatureIds.mouse1));
	
    

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
  /*this.AddObserver=AddObserver;
  this.Notify = Notify;
  this.ObserverUpdate = ObserverUpdate;*/
  this.ForbiddenLocationCreatureKeys;
  this.IsForbiddenPosition=IsForbiddenPosition;
  
}

function Notify()
{
  this.observer.ObserverUpdate();
}

function AddObserver(observer)
{
  this.observer=observer;
}

function ObserverUpdate()
{
  
}

function Mouse(mouseLocationId, mouseCageId, mouseFreeId,timeInterval)
{
  this.mouseLocationId = mouseLocationId;
  this.mouseHtmlId= mouseCageId;
  this.mouseFreeId= mouseFreeId;
  this.timeInterval=timeInterval;
  this.isMouseInCage=true;
  this.InitMouse=InitMouse;
  this.SetTimeIntervalToActivateMouse = SetTimeIntervalToActivateMouse;
  this.MoveMouseToId=MoveMouseToId;
  this.showMouse=showMouse;
  this.FreeMouseFromCage=FreeMouseFromCage;
  this.DistractSnake=DistractSnake;
}


function InitMouse()
{
   this.SetTimeIntervalToActivateMouse();
   
}

function SetTimeIntervalToActivateMouse()
{
  var mouse=this;
  setTimeout(function(){mouse.showMouse();},3000);
}

function showMouse()
{
  this.MoveMouseToId(this.mouseLocationId);
  var id=this.mouseHtmlId;
  $("#"+id).fadeIn(5000);
  var mouse=this;
  $("#"+this.mouseHtmlId).click(function(){mouse.FreeMouseFromCage();});
 
 }

 function FreeMouseFromCage()
 {
   $("#"+this.mouseHtmlId).css('display','none');
   this.mouseHtmlId="freeMouse";
   this.MoveMouseToId(this.mouseLocationId);
   $("#"+this.mouseHtmlId).show();
   var mouse=this;
   setTimeout(mouse.DistractSnake,this.timeInterval);
 }
 
 function DistractSnake()
 {
    strategyArrayToAvoidSnakeAndHuman = new Array();
   if(IsCreature1ToRightOfCreature2(snakey1.snakeLocationId,mouse.mouseLocationId))
   {
     if(IsCreature1BelowCreature2(snakey1.snakeLocationId,mouse.mouseLocationId))
	   accessArray[LR];
	  else accessArray[UR]; 
   }
   else
   {
     if(IsCreature1BelowCreature2(snakey1.snakeLocationId,mouse.mouseLocationId))
	   accessArray[LL];
	  else accessArray[UL]; 
   }
 }

function InitSnake(forbiddenLocationCreatureKeys)
{
  this.ForbiddenLocationCreatureKeys = forbiddenLocationCreatureKeys;
  this.MoveSnakeToId(this.snakeLocationId);
$(document.getElementById(this.snakeHtmlId)).show();
  this.SetTimeIntervalForSnakeMovement();
}





function SetTimeIntervalForSnakeMovement() {
    
    var snake=this;
    snake.timeOutBinding = setTimeout(function(){snake.moveSnake();}, snake.timeInterval);
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

function MoveMouseToId(id)
{
    this.mouseLocationId=id;
    locationsOfAllCreatures[this.mouseHtmlId] = id;
     MoveToSameLocationAsObject(document.getElementById(this.mouseHtmlId),document.getElementById(id));
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