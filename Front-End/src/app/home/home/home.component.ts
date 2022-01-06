import { Component, OnInit } from '@angular/core';




 //container to hold all different shapes on it
var shapesBack:shapeBack[] = [];

//----------------------------------------------------------------------//

//mapping between shape ID and its area on canvas
let machineArea = new Map<string, Path2D>();
let queueArea = new Map<string, Path2D>();


//----------------------------------------------------------------------//

//flag to activate buttons

var draw_line : shapeBack|null = null;

//----------------------------------------------------------------------//

//flag to activate buttons of creation
var create_line_flag : boolean = false;
var created_line : boolean = false;

var create_circle_flag : boolean = false;
var created_circle : boolean = false;

var create_rect_flag : boolean = false;
var created_rect : boolean = false;

var circleButtonFlag : boolean = false;
var rectButtonFlag : boolean = false;
var lineButtonFlag : boolean = false;


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

//----------------------------------------------------------------------//

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  title = 'Front-End';

  constructor() {}



//----------------------------------------------------------------------//



  drawShape(shape : shapeBack, fillcolor : string){
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
      case "circle":
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
        queueArea.set(ID, area);
        area = null;
        break;
      case "rect" :
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
        machineArea.set(ID, area);
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
          canvasGlobal.moveTo(width,height);
          canvasGlobal.arc(width,height,20,angle1,angle2,true);
          canvasGlobal.lineTo(width,height);
          canvasGlobal.fill();
          canvasGlobal.closePath();


          area = null;
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
      create_circle_flag = false;
      create_rect_flag = false;
      created_circle = false;
      created_rect = false;
      var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
      var canvasGlobal = boardGlobal.getContext("2d")!;

      create_line_flag = true;
      created_line = false;

      var selectLine = false;
      boardGlobal.addEventListener("mousedown",e=>{

        if(!created_line &&  lineButtonFlag){
          draw_line={
          x : e.offsetX,
          y :e.offsetY,
          width : 0,
          height : 0,
          stCo : "white",
          fiCo : "black",
          type : "line",
          is_filled : 1,
          stWi : 1,
          shapeID : get_new_ID()
          }
          selectLine = true;
          created_line = true
        }


      });

      boardGlobal.addEventListener("mousemove", e => {
        if(create_line_flag && selectLine && (draw_line != null) && lineButtonFlag && created_line){
          canvasGlobal.clearRect(0,0,1380,675);

          draw_line.width = e.offsetX;
          draw_line.height = e.offsetY;

          this.drawShape(draw_line, "");
          for(var i = 0; i < shapesBack.length; i++){
            this.drawShape(shapesBack[i], "");
          }
        }

      });
      boardGlobal.addEventListener("mouseup", e => {
        if(lineButtonFlag){
          create_line_flag =false;
          created_line = true;
          selectLine = false;
        if(draw_line != null && (draw_line.width != 0 && draw_line.height != 0)){

            this.drawShape(draw_line, "");
            shapesBack.push(draw_line);

        }

        draw_line = null;

        document.getElementById("line")!.style.backgroundColor = "transparent"
        }

      });

      if(create_line_flag){
        document.getElementById("line")!.style.backgroundColor = "rgba(58, 57, 57, 0.856)"

      }


    }

//----------------------------------------------------------------------//

  createCircle(){
    create_line_flag = false;
    create_rect_flag = false;

    created_line = false;
    created_rect = false;


    create_circle_flag = true;
    created_circle = false;


    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var circle : shapeBack;

    boardGlobal.addEventListener("mousedown", e=> {

      if(!created_circle  && circleButtonFlag){

        circle={
          x : e.offsetX,
          y : e.offsetY,
          width : 60,
          height : 60,
          stCo : "white",
          fiCo : "darkred",
          type : "circle",
          is_filled : 1,
          stWi : 2,
          shapeID : get_new_ID()
          }
        create_circle_flag = false;
        created_circle = true;
        console.log(circle);
        this.drawShape(circle, "");
        shapesBack.push(circle);
        }

    });


    boardGlobal.addEventListener("mouseup",e=>{
      if(circleButtonFlag){
        created_circle = true;
        create_circle_flag = false;
        circle = null;

        document.getElementById("circle")!.style.backgroundColor = "transparent"

      }

    });
    if(create_circle_flag){
      document.getElementById("circle")!.style.backgroundColor = "rgba(58, 57, 57, 0.856)"

    }
}

//----------------------------------------------------------------------//

  createRect(){
    create_line_flag = false;
    create_circle_flag = false;

    created_line = false;
    created_circle = false;


    create_rect_flag = true;
    created_rect = false;


    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var rect : shapeBack;

    boardGlobal.addEventListener("mousedown",e=>{

      if(!created_rect && rectButtonFlag){

        rect={
          x : e.offsetX,
          y :e.offsetY,
          width : 90,
          height : 50,
          stCo : "white",
          fiCo : "darkgreen",
          type : "rect",
          is_filled : 1,
          stWi : 2,
          shapeID : get_new_ID()
          }
          create_rect_flag =false;
          created_rect = true;
          this.drawShape(rect, "");
          shapesBack.push(rect);
        }

    });


    boardGlobal.addEventListener("mouseup",e=>{
      if(rectButtonFlag){
        created_rect = true;
        create_rect_flag = false;
        rect = null;
        document.getElementById("rect")!.style.backgroundColor = "transparent"
      }

    });
    if(create_rect_flag){
      document.getElementById("rect")!.style.backgroundColor = "rgba(58, 57, 57, 0.856)"

    }
  }

//----------------------------------------------------------------------//


  clearAll(){
    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;
    canvasGlobal.clearRect(0,0,1380,675)
    machineArea.clear()
    queueArea.clear()
    shapesBack = []
  }

  //----------------------------------------------------------------------//

  disableButtons(){
    if(create_line_flag){
      circleButtonFlag = false;
      rectButtonFlag  = false;


      lineButtonFlag = true;
    }

    if(create_circle_flag){



      rectButtonFlag = false;
      lineButtonFlag = false;
      circleButtonFlag = true;
      draw_line = null;
    }
    if(create_rect_flag){
      circleButtonFlag  = false;
      lineButtonFlag = false;
      rectButtonFlag = true;
      draw_line = null;
    }

    if(!create_rect_flag){
      document.getElementById("rect")!.style.backgroundColor = "transparent"

    }
    if(!create_circle_flag){
      document.getElementById("circle")!.style.backgroundColor = "transparent"

    }
    if(!create_line_flag){
      document.getElementById("line")!.style.backgroundColor = "transparent"

    }


  }



}
//----------------------------------------------------------------------//

