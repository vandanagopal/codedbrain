$(document).ready(function() {
    init();
});

function init() {
    humanLocationId = 2;
    direction = {"left":0,"right":1,"up":2,"down":3}
    board = {numberOfCells : 36,numberOfColumns : 6}
    var snake1=new Snake(30,"snakey1",1000);
    var snake2=new Snake(5,"snakey2", 500);
    var mouse1= new Mouse(10,"mouseCage1","" );
    SetMouseOverCallBack();
    SetObjectToBeDraggedWithMouse("humanPic");
    snake1.InitSnake();
    snake2.InitSnake();
    snake1.AddObserver(snake2);
    snake2.AddObserver(snake1);
    mouse1.InitMouse();
    InitHuman();
    

}

function InitHuman()
{
    dragObject.style.position = 'absolute';
    dragObject.style.top = document.getElementById("2").offsetTop +8;
    dragObject.style.left = document.getElementById("2").offsetLeft + 8;
    $(document.getElementById("humanPic")).show();
}


function Snake(snakeLocationId, snakeId, timeInterval)
{
  this.snakeLocationId=snakeLocationId;
  this.snakeId=snakeId;
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
  this.ShouldMoveLeft=ShouldMoveLeft;
  this.ShouldMoveUp=ShouldMoveUp;
  this.AddObserver=AddObserver;
  this.Notify = Notify;
  this.ObserverUpdate = ObserverUpdate
  
}

function Notify()
{
  this.observer.ObserverUpdate();
}

function AddObserver(observer)
{
  this.observer=observer;
}

function Mouse(mouseLocationId, mouseCageId, mouseFreeId)
{
  this.mouseLocationId = mouseLocationId;
  this.mouseId= mouseCageId;
  this.mouseFreeId= mouseFreeId;
  this.isMouseInCage=true;
  this.InitMouse=InitMouse;
  this.SetTimeIntervalToShowMouse = SetTimeIntervalToShowMouse;
  this.MoveMouseToId=MoveMouseToId;
  this.showMouse=showMouse;
}

function InitMouse()
{
   this.MoveMouseToId(this.mouseLocationId);
   this.SetTimeIntervalToShowMouse();
}

function SetTimeIntervalToShowMouse()
{
  var mouse=this;
  setTimeout(function(){mouse.showMouse();},3000);
}

function showMouse()
{
  var id=this.mouseId;
  $("#"+id).fadeIn(5000);
}


function InitSnake()
{
  this.MoveSnakeToId(this.snakeLocationId);
$(document.getElementById(this.snakeId)).show();
  this.SetTimeIntervalForSnakeMovement();
}

function SetMouseOverCallBack() {
    $("td").mouseover(moveHuman);
}

function SetObjectToBeDraggedWithMouse(id) {
    dragObject = document.getElementById(id);
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
  
if(this.snakeLocationId==humanLocationId) return;
var shouldMoveRowWiseFirst = Math.floor(Math.random() * 2);
if (shouldMoveRowWiseFirst ) this.MoveRowWiseFirst();
else this.MoveColumnWiseFirst();
     
}

function MoveRowWiseFirst() {

    if (!AreSnakeAndHumanInSameColumn(this)) this.MoveSnakeLeftOrRight();
    else this.MoveSnakeUpOrDown();

}

function AreSnakeAndHumanInSameColumn(snake) {
    if (snake.snakeLocationId % board.numberOfColumns == humanLocationId % board.numberOfColumns) return true;
    else return false;
}

function MoveSnakeLeftOrRight() {
    if (this.ShouldMoveLeft()) this.MoveSnakeToId(this.snakeLocationId - 1);

    else this.MoveSnakeToId(this.snakeLocationId + 1);
}

function ShouldMoveLeft()
{
 if(this.snakeLocationId % 6 > humanLocationId % board.numberOfColumns)
   return true;
 else return false;
}

function ShouldMoveUp()
{
 if(this.snakeLocationId > humanLocationId)
   return true;
 else return false;
}

function MoveSnakeUpOrDown() {
    if (this.ShouldMoveUp()) this.MoveSnakeToId(this.snakeLocationId - board.numberOfColumns);

    else this.MoveSnakeToId(this.snakeLocationId + board.numberOfColumns);
}

function MoveColumnWiseFirst() {

    if (!AreSnakeAndHumanInSameRow(this)) this.MoveSnakeUpOrDown();

    else this.MoveSnakeLeftOrRight();
}

function AreSnakeAndHumanInSameRow(snake) {
    if (Math.floor(snake.snakeLocationId / board.numberOfColumns) == Math.floor(humanLocationId / board.numberOfColumns)) return true;
    else return false;
}

function MoveMouseToId(id)
{
    this.mouseLocationId=id;
    MoveToSameLocationAsObject(document.getElementById(this.mouseId),document.getElementById(id));
}


function MoveSnakeToId(id)
{
    this.snakeLocationId=id;
    MoveToSameLocationAsObject(document.getElementById(this.snakeId),document.getElementById(id));
}

function MoveToSameLocationAsObject(objectToBeMoved, targetObject)
{
    objectToBeMoved.style.position = 'absolute';
    objectToBeMoved.style.top = targetObject.offsetTop +8;
    objectToBeMoved.style.left = targetObject.offsetLeft + 8;
}

function moveHuman(ev) 
{

    MoveToSameLocationAsObject(dragObject,this);
    humanLocationId = this.id;

}