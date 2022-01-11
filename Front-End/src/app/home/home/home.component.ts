import { HomeService } from './home.service';
import { productionNetworkElement } from './productionNetworkElement';
import { Component, OnInit } from '@angular/core';
import { interval, Observable } from 'rxjs';
import { delay, switchMap } from 'rxjs/operators';

 //container to hold all different shapes on it
var shapesBack:shapeBack[] = [];

//----------------------------------------------------------------------//

//mapping between shape ID and its area on canvas
let machineArea = new Map<string, Path2D>();
let queueArea = new Map<string, Path2D>();
let forwardProductionNetwork = new Map<string,string[]>();
let backwardProductionNetwork = new Map<string,string[]>();


//----------------------------------------------------------------------//

//flag to activate buttons

var draw_line : shapeBack = null;

//----------------------------------------------------------------------//

//flag to activate buttons of creation

var createLineFlag : boolean = false;
var createdLine : boolean = false;

var createMachineFlag : boolean = false;
var createdMachine : boolean = false;

var createQueueFlag : boolean = false;
var createdQueue : boolean = false;

var machineButtonFlag : boolean = false;
var queueButtonFlag : boolean = false;
var lineButtonFlag : boolean = false;


var tempType : string = "";

//----------------------------------------------------------------------//

//global values to stroke color and witdth to assign all shapes to it
var strokeColor:string = 'black';
var strokeWidth:number = 3;

//----------------------------------------------------------------------//
// array for ID generator
var serial = Array.from(Array(1000000).keys());

//----------------------------------------------------------------------//

//ID generator which give ID and remove it from the ID generator array
function get_new_ID():string {
  var ID =   serial.pop()
  return (ID.toString())

}
//----------------------------------------------------------------------//

//shape interface to cover all shapes under restricted contract
export interface shapeBack{
  x:number;
  y:number;
  width:number;
  height:number;
  fiCo:string;
  stCo:string;
  stWi:number;
  type:string;
  is_filled:number;
  shapeID:string;
}

export interface elementsMap {
  elementKey:string[];
  values:string;
}

export interface Product {
  color : string;
}

export interface Machine {
  machineName : string;
  product : Product;
  serviceTime : number;
  prevBufferQueues : Buffer[];
  nexrtBufferQueues : Buffer[];
}

export interface Buffer {
  products : Product[];
  bufferID : string;
  size : number;
}

//----------------------------------------------------------------------//

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  ngOnInit(): void {
    var queue : shapeBack;

    queue={
      x : 3,
      y : 3,
      width : 90,
      height : 50,
      stCo : "white",
      fiCo : "darkgreen",
      type : "queue",
      is_filled : 1,
      stWi : 2,
      shapeID : "Queue".concat(get_new_ID())
      }
      createQueueFlag =false;
      createdQueue = true;
      this.placeElement(queue, "");
      shapesBack.push(queue);
    
  }

  title = 'Front-End';
  constructor(private server: HomeService) {}

  playEvent : any;
  stopReplay :boolean;


  placeElement(shape : shapeBack, fillcolor : string){
    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var x = shape.x;
    var y = shape.y;
    var width = shape.width;
    var height = shape.height;
    var stCo = shape.stCo;
    var fiCo = shape.fiCo;
    var stWi = shape.stWi;
    var isfilled = shape.is_filled;
    var type = shape.type;
    var ID = shape.shapeID;



    var area:Path2D|null = new Path2D();
    switch(type){
      case "machine":
        if(fillcolor != ""){
          shape.fiCo = fillcolor
          fiCo = fillcolor;
        }
        if(isfilled == 1){
          area.arc(x, y, 0.5*width, 0, 2*Math.PI);
          canvasGlobal.beginPath();
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.fillStyle = fiCo;
          canvasGlobal.arc(x, y, 0.5*width, 0, 2*Math.PI);
          canvasGlobal.fill();
          canvasGlobal.stroke();
        }
        else{
          area.arc(x, y, 0.5*width, 0, 2*Math.PI);
          canvasGlobal.beginPath();
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.arc(x, y, 0.5*width, 0, 2*Math.PI);
          canvasGlobal.stroke();
        }
        machineArea.set(ID, area);
        area = null;
        break;
      case "queue" :
        if(fillcolor != ""){
          shape.fiCo = fillcolor
          fiCo = fillcolor;
        }
        if(isfilled == 1){
          area.rect(x, y, width, height);
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.fillStyle = fiCo;
          canvasGlobal.beginPath();
          canvasGlobal.rect(x, y, width, height);
          canvasGlobal.fill()
          canvasGlobal.stroke();
        }
        else{
          area.rect(x, y, width, height);
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.beginPath();
          canvasGlobal.rect(x, y, width, height);
          canvasGlobal.stroke();
        }
        queueArea.set(ID, area);
        area = null;
        break;
        case "line":

          area.moveTo(x, y);
          area.lineTo(width, height);
          area.closePath;
          canvasGlobal.beginPath();
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.moveTo(x, y);
          canvasGlobal.lineTo(width, height);
          canvasGlobal.closePath();
          canvasGlobal.stroke();
          var angle=Math.PI+Math.atan2(height-y,width-x);
          var angle1=angle+Math.PI/6;
          var angle2=angle-Math.PI/6;
          canvasGlobal.beginPath();
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.fillStyle = "white"
          canvasGlobal.moveTo(width,height);
          canvasGlobal.arc(width,height,20,angle1,angle2,true);
          canvasGlobal.lineTo(width,height);
          canvasGlobal.fill();
          canvasGlobal.closePath();

          break;
        default:
          break;
    }

  }

//----------------------------------------------------------------------//

  confirm_stroke() {
    var sc = <HTMLInputElement>document.getElementById("stroke_color");
    strokeColor = sc.value;
    var sw = <HTMLInputElement>document.getElementById("stroke_width");
    var strwid : number = parseInt(sw.value);
    strokeWidth = strwid;
  }

//----------------------------------------------------------------------//
    createLine(){
      createMachineFlag = false;
      createQueueFlag = false;

      createdMachine = false;
      createdQueue = false;

      var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
      var canvasGlobal = boardGlobal.getContext("2d")!;



      createLineFlag = true;
      createdLine = false;

      var fromElement:string;
      var selectLine = false;
      boardGlobal.addEventListener("mousedown",e=>{
        if(!createdLine &&  lineButtonFlag ){
          for(var shape of shapesBack){
              if(shape.type == "machine"){
                if(canvasGlobal.isPointInPath( machineArea.get(shape.shapeID),e.offsetX,e.offsetY)){
                  draw_line={
                    x : e.offsetX,
                    y :e.offsetY,
                    width : 0,
                    height : 0,
                    stCo : "white",
                    fiCo : "black",
                    type : "line",
                    is_filled : 1,
                    stWi : 0.60,
                    shapeID : get_new_ID()
                    }
                    selectLine = true;
                    createdLine = true
                    tempType = "machine";
                    fromElement = shape.shapeID;

                }
              }else if(shape.type == "queue"){
                if(canvasGlobal.isPointInPath( queueArea.get(shape.shapeID),e.offsetX,e.offsetY)){
                  console.log("INSIDE IF MOUSE DOWN");
                  draw_line={
                    x : e.offsetX,
                    y :e.offsetY,
                    width : 0,
                    height : 0,
                    stCo : "white",
                    fiCo : "black",
                    type : "line",
                    is_filled : 1,
                    stWi : 0.60,
                    shapeID : get_new_ID()
                    }
                    selectLine = true;
                    createdLine = true
                    tempType = "queue";
                    fromElement = shape.shapeID;

                }
              }
          }
        }
      });

      boardGlobal.addEventListener("mousemove", e => {
        if(createLineFlag && selectLine && (draw_line != null) && lineButtonFlag && createdLine){
          canvasGlobal.clearRect(0,0,1380,675);

          draw_line.width = e.offsetX;
          draw_line.height = e.offsetY;

          this.placeElement(draw_line, "");
          for(var i = 0; i < shapesBack.length; i++){
            this.placeElement(shapesBack[i], "");
          }
        }

      });
      boardGlobal.addEventListener("mouseup", e => {
        if(lineButtonFlag && fromElement != null){
          for(var shape of shapesBack){
            switch(tempType.concat(shape.type)){
              case "queuemachine":

                if(canvasGlobal.isPointInPath( machineArea.get(shape.shapeID),e.offsetX,e.offsetY)){
                  console.log("INSIDE CASE 1");

                  createLineFlag =false;
                  createdLine = true;
                  selectLine = false;
                  lineButtonFlag = false;

                  console.log(draw_line);
                  if(draw_line != null && (draw_line.width != 0 && draw_line.height != 0)){

                    this.placeElement(draw_line, "");
                    shapesBack.push(draw_line);
                  }
                  draw_line = null;
                  document.getElementById("line")!.style.backgroundColor = "transparent";

                  if(backwardProductionNetwork.has(shape.shapeID)){
                    backwardProductionNetwork.get(shape.shapeID)?.push(fromElement)
                  }else{
                    backwardProductionNetwork.set(shape.shapeID, [fromElement])
                  }
                  console.log(backwardProductionNetwork);
                  fromElement = null;
                }
                break;


              case "machinequeue":
                if(canvasGlobal.isPointInPath( queueArea.get(shape.shapeID),e.offsetX,e.offsetY)){
                console.log("INSIDE CASE 2");

                  createLineFlag = false;
                  createdLine = true;
                  selectLine = false;
                  lineButtonFlag = false;

                  if(draw_line != null && (draw_line.width != 0 && draw_line.height != 0)){
                    this.placeElement(draw_line, "");
                    shapesBack.push(draw_line);
                  }
                  draw_line = null;
                  document.getElementById("line")!.style.backgroundColor = "transparent";
                  console.log(fromElement);
                  if(forwardProductionNetwork.has(fromElement)){
                    forwardProductionNetwork.get(fromElement)?.push(shape.shapeID)
                  }else{
                    forwardProductionNetwork.set(fromElement, [shape.shapeID])

                  }

                  fromElement = null;
                  console.log(forwardProductionNetwork);

                }
                break;


                default :
                  canvasGlobal.clearRect(0,0,1380,675);
                  createLineFlag =false;
                  createdLine = true;
                  selectLine = false;
                  lineButtonFlag = false;
                  document.getElementById("line")!.style.backgroundColor = "transparent"
                  for(var i = 0; i < shapesBack.length; i++){
                    this.placeElement(shapesBack[i], "");
                  }
                  break;


              }
            }



          }
          draw_line = null;


          for(var i = 0; i < shapesBack.length; i++){
            this.placeElement(shapesBack[i], "");
          }

        tempType = "";


      });

      if(createLineFlag){
        document.getElementById("line")!.style.backgroundColor = "rgba(58, 57, 57, 0.856)"

      }

    }


//----------------------------------------------------------------------//

  createMachine(){
    createLineFlag = false;
    createQueueFlag = false;

    createdLine = false;
    createdQueue = false;


    createMachineFlag = true;
    createdMachine = false;


    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var machine : shapeBack;

    boardGlobal.addEventListener("mousedown", e=> {

      if(!createdMachine && machineButtonFlag){

        machine={
          x : e.offsetX,
          y : e.offsetY,
          width : 60,
          height : 60,
          stCo : "white",
          type : "machine",
          fiCo : "darkred",
          is_filled : 1,
          stWi : 2,
          shapeID : "Machine".concat(get_new_ID())
          }
        createMachineFlag = false;
        createdMachine = true;
        console.log(machine);
        this.placeElement(machine, "");
        shapesBack.push(machine);
        }

    });


    boardGlobal.addEventListener("mouseup",e=>{
      if(machineButtonFlag){
        createdMachine = true;
        createMachineFlag = false;
        machine = null;

        document.getElementById("machine")!.style.backgroundColor = "transparent"


      }

    });
    if(createMachineFlag){
      document.getElementById("machine")!.style.backgroundColor = "rgba(58, 57, 57, 0.856)"

    }
}

//----------------------------------------------------------------------//

  createQueue(){
    createLineFlag = false;
    createMachineFlag = false;

    createdLine = false;
    createdMachine = false;


    createQueueFlag = true;
    createdQueue = false;


    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var queue : shapeBack;

    boardGlobal.addEventListener("mousedown",e=>{

      if(!createdQueue && queueButtonFlag){

        queue={
          x : e.offsetX,
          y :e.offsetY,
          width : 90,
          height : 50,
          stCo : "white",
          fiCo : "darkgreen",
          type : "queue",
          is_filled : 1,
          stWi : 2,
          shapeID : "Queue".concat(get_new_ID())
          }
          createQueueFlag =false;
          createdQueue = true;
          this.placeElement(queue, "");
          shapesBack.push(queue);
        }

    });


    boardGlobal.addEventListener("mouseup",e=>{
      if(queueButtonFlag){
        createdQueue = true;
        createQueueFlag = false;
        queue = null;


        document.getElementById("queue")!.style.backgroundColor = "transparent"

      }

    });
    if(createQueueFlag){
      document.getElementById("queue")!.style.backgroundColor = "rgba(58, 57, 57, 0.856)"

    }
  }

//----------------------------------------------------------------------//


  clearAll(){
    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;
    canvasGlobal.clearRect(0,0,1380,675);
    machineArea.clear();
    queueArea.clear();
    shapesBack = [];
    forwardProductionNetwork.clear();
    backwardProductionNetwork.clear();
  }

//----------------------------------------------------------------------//


run(){


  var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
  var canvasGlobal = boardGlobal.getContext("2d")!;

  console.log(forwardProductionNetwork);

  console.log(forwardProductionNetwork.size)
  var temp : string[][][][] = [];

  temp.push([]);
  temp.push([]);

  let ctr = 0;

  forwardProductionNetwork.forEach((key, value) => {

    temp[0].push([]);
    temp[0][ctr].push([]);
    temp[0][ctr].push([]);
    temp[0][ctr][1] = key;
    temp[0][ctr][0].push(value);
    ctr++;
    console.log("Key ", key);
    console.log("value ", value);

  })
  ctr = 0;
  backwardProductionNetwork.forEach((key, value) => {
    temp[1].push([]);
    temp[1][ctr].push([]);
    temp[1][ctr].push([]);
    temp[1][ctr][1] = key;
    temp[1][ctr][0].push(value);
    ctr++;
    console.log("Key ", key);
    console.log("value ", value);

  })

  const convMap = Object.create(null)
  forwardProductionNetwork.forEach((val: string[], key: string) => {
    convMap[key] = val;
  });
  const convMap2 = Object.create(null)
  backwardProductionNetwork.forEach((val: string[], key: string) => {
    convMap2[key] = val;
  });

  console.log();

  console.log(JSON.stringify(temp))
  this.server.generateNetwork(JSON.stringify(convMap)).subscribe((data)=>{
    this.server.generateNetwork(JSON.stringify(convMap2)).subscribe((data)=>{
        this.server.play().subscribe((data)=>{
          var serv = this.server;
          var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
          var canvasGlobal = boardGlobal.getContext("2d")!;
          this.playEvent = setInterval(function(this:any){
            serv.polling().subscribe((x : Object[]) => {
              if(x != null){
                canvasGlobal.clearRect(0,0,1380,675);

                let ctr = 0;

                var machines : Machine[] = Object.assign(x[0]);
                var buffers : Buffer[] = Object.assign(x[1]);
                console.log(buffers.length)



                for(let i = 0; i < machines.length; i++){
                  var color;

                  try{
                    color = machines[i].product.color;
                  }
                  catch(e){
                    color = "darkred"
                  }
                  var machineID = machines[i].machineName;
                  var areaMachine = machineArea.get(machineID);


                  for(var shape of shapesBack){

                    ctr++;
                    if(shape.shapeID == machineID){
                      areaMachine.arc(shape.x, shape.y, 0.5*shape.width, 0, 2*Math.PI);
                      canvasGlobal.beginPath();
                      canvasGlobal.strokeStyle = shape.stCo;
                      canvasGlobal.lineWidth = shape.stWi;
                      canvasGlobal.fillStyle = color;
                      canvasGlobal.arc(shape.x, shape.y, 0.5*shape.width, 0, 2*Math.PI);
                      canvasGlobal.fill();
                      canvasGlobal.stroke();
                      machineArea.set(machineID, areaMachine);
                    }
                  }

                  areaMachine = null;
                }

                ctr = 0;

                for(let i = 0; i < buffers.length; i++){

                  var bufferID = buffers[i].bufferID;
                  var areaBuffer = queueArea.get(bufferID);

                  console.log(bufferID);
                  for(var shape of shapesBack){
                    ctr++;
                    if(shape.shapeID == bufferID){
                      areaBuffer.rect(shape.x, shape.y, shape.width, shape.height);
                      canvasGlobal.strokeStyle = shape.stCo;
                      canvasGlobal.lineWidth = shape.stWi;
                      canvasGlobal.fillStyle = "rgb(0,100,0)"
                      canvasGlobal.beginPath();
                      canvasGlobal.rect(shape.x, shape.y, shape.width, shape.height)
                      canvasGlobal.font = "25px Arial";
                      canvasGlobal.fill();
                      canvasGlobal.stroke();

                      canvasGlobal.strokeText(buffers[i].size.toString(), shape.x+(shape.width/2), shape.y+(shape.height/2));
                      canvasGlobal.textAlign="center";
                      canvasGlobal.textBaseline = "middle";

                      queueArea.set(bufferID, areaBuffer);
                    }

                  }
                  areaBuffer = null;
                }
              }

              for(var shape of shapesBack){
                if(shape.type == "line"){
                  var lineArea = new Path2D();
                  lineArea.moveTo(shape.x, shape.y);
                  lineArea.lineTo(shape.width, shape.height);
                  lineArea.closePath;
                  canvasGlobal.beginPath();
                  canvasGlobal.strokeStyle = shape.stCo;
                  canvasGlobal.lineWidth = shape.stWi;
                  canvasGlobal.moveTo(shape.x, shape.y);
                  canvasGlobal.lineTo(shape.width, shape.height);
                  canvasGlobal.closePath();
                  canvasGlobal.stroke();
                  var angle=Math.PI+Math.atan2(shape.height-shape.y,shape.width-shape.x);
                  var angle1=angle+Math.PI/6;
                  var angle2=angle-Math.PI/6;
                  canvasGlobal.beginPath();
                  canvasGlobal.strokeStyle = shape.stCo;
                  canvasGlobal.lineWidth = shape.stWi;
                  canvasGlobal.fillStyle = "white"
                  canvasGlobal.moveTo(shape.width,shape.height);
                  canvasGlobal.arc(shape.width,shape.height,20,angle1,angle2,true);
                  canvasGlobal.lineTo(shape.width,shape.height);
                  canvasGlobal.fill();
                  canvasGlobal.closePath();
                  lineArea = null;
                }

              }

            });
          }, 1)
        });
    });
  });
}
//----------------------------------------------------------------------//
replay(){
  clearInterval(this.playEvent);
    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;
    this.stopReplay = false;
    this.server.replay().subscribe((snapshots : Object[][]) => {
      console.log(snapshots);
      let replayCtr = 0;
       setInterval(function(){
        if(snapshots[replayCtr] != null){
          canvasGlobal.clearRect(0,0,1380,675);

          let ctr = 0;

          var machines : Machine[] = Object.assign(snapshots[replayCtr][0]);
          var buffers : Buffer[] = Object.assign(snapshots[replayCtr][1]);
          console.log(buffers.length)



          for(let i = 0; i < machines.length; i++){
            var color;
            
            try{
              color = machines[i].product.color;
            }
            catch(e){
              color = "darkred"
            }
            var machineID = machines[i].machineName;
            var areaMachine = machineArea.get(machineID);


            for(var shape of shapesBack){

              ctr++;
              if(shape.shapeID == machineID){
                areaMachine.arc(shape.x, shape.y, 0.5*shape.width, 0, 2*Math.PI);
                canvasGlobal.beginPath();
                canvasGlobal.strokeStyle = shape.stCo;
                canvasGlobal.lineWidth = shape.stWi;
                canvasGlobal.fillStyle = color;
                canvasGlobal.arc(shape.x, shape.y, 0.5*shape.width, 0, 2*Math.PI);
                canvasGlobal.fill();
                canvasGlobal.stroke();
                machineArea.set(machineID, areaMachine);
              }
            }

            areaMachine = null;
          }
          ctr = 0;
          for(let i = 0; i < buffers.length; i++){

            var bufferID = buffers[i].bufferID;
            var areaBuffer = queueArea.get(bufferID);

            console.log(bufferID);
            for(var shape of shapesBack){
              ctr++;
              if(shape.shapeID == bufferID){
                areaBuffer.rect(shape.x, shape.y, shape.width, shape.height);
                canvasGlobal.strokeStyle = shape.stCo;
                canvasGlobal.lineWidth = shape.stWi;
                canvasGlobal.fillStyle = "rgb(0,100,0)"
                canvasGlobal.beginPath();
                canvasGlobal.rect(shape.x, shape.y, shape.width, shape.height)
                canvasGlobal.font = "25px Arial";
                canvasGlobal.fill();
                canvasGlobal.stroke();

                canvasGlobal.strokeText(buffers[i].size.toString(), shape.x+(shape.width/2), shape.y+(shape.height/2));
                canvasGlobal.textAlign="center";
                canvasGlobal.textBaseline = "middle";

                queueArea.set(bufferID, areaBuffer);
              }

            }
            areaBuffer = null;
          }
        }

        for(var shape of shapesBack){
          if(shape.type == "line"){
            var lineArea = new Path2D();
            lineArea.moveTo(shape.x, shape.y);
            lineArea.lineTo(shape.width, shape.height);
            lineArea.closePath;
            canvasGlobal.beginPath();
            canvasGlobal.strokeStyle = shape.stCo;
            canvasGlobal.lineWidth = shape.stWi;
            canvasGlobal.moveTo(shape.x, shape.y);
            canvasGlobal.lineTo(shape.width, shape.height);
            canvasGlobal.closePath();
            canvasGlobal.stroke();
            var angle=Math.PI+Math.atan2(shape.height-shape.y,shape.width-shape.x);
            var angle1=angle+Math.PI/6;
            var angle2=angle-Math.PI/6;
            canvasGlobal.beginPath();
            canvasGlobal.strokeStyle = shape.stCo;
            canvasGlobal.lineWidth = shape.stWi;
            canvasGlobal.fillStyle = "white"
            canvasGlobal.moveTo(shape.width,shape.height);
            canvasGlobal.arc(shape.width,shape.height,20,angle1,angle2,true);
            canvasGlobal.lineTo(shape.width,shape.height);
            canvasGlobal.fill();
            canvasGlobal.closePath();
            lineArea = null;
          }
        }
        replayCtr++;
       }, 1) 
        
      
    });

}

//----------------------------------------------------------------------//
stop(){
  this.server.stop().subscribe((data:string)=>{
    console.log(data)
    clearInterval(this.playEvent)
    this.stopReplay = true;

  });
}

//----------------------------------------------------------------------//

  disableButtons(){
    if(createLineFlag){

      machineButtonFlag = false;
      queueButtonFlag  = false;


      lineButtonFlag = true;


    }

    if(createMachineFlag){



      queueButtonFlag = false;
      lineButtonFlag = false;
      machineButtonFlag = true;

      draw_line = null;
    }
    if(createQueueFlag){

      machineButtonFlag  = false;
      lineButtonFlag = false;

      queueButtonFlag = true;
      draw_line = null;
    }


    if(!createQueueFlag){
      document.getElementById("queue")!.style.backgroundColor = "transparent"

    }
    if(!createMachineFlag){
      document.getElementById("machine")!.style.backgroundColor = "transparent"

    }
    if(!createLineFlag){
      document.getElementById("line")!.style.backgroundColor = "transparent"

    }


  }

//----------------------------------------------------------------------//


}
