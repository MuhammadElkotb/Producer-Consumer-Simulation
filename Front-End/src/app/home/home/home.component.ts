import { Component, OnInit } from '@angular/core';




 //container to hold all different shapes on it
var shapesBack:shapeBack[] = [];

//----------------------------------------------------------------------//

//mapping between shape ID and its area on canvas
let canvasArea = new Map<string, Path2D>();

//----------------------------------------------------------------------//

//flag to activate buttons
var remove_flag :boolean = false;
var move_flag :boolean = false;
var resize_flag :boolean = false;
var fill_flag :boolean = false;
var copy_flag : boolean = false;
var undo_flag : boolean = false;
var redo_flag : boolean = false;
var found_move : boolean = false;
var found_copy : boolean = false;
var found_resize : boolean = false;
var draw_line : shapeBack|null = null;

//----------------------------------------------------------------------//

//flag to activate buttons of creation
var create_line_flag : boolean = false;
var created_line : boolean = false;

var create_circle_flag : boolean = false;
var created_circle : boolean = false;

var create_rect_flag : boolean = false;
var created_rect : boolean = false;

var create_square_flag : boolean = false;
var created_square : boolean = false;

var create_ellipse_flag : boolean = false;
var created_ellipse : boolean = false;

var create_triangle_flag : boolean = false;
var created_triangle : boolean = false;

var circleButtonFlag : boolean = false;
var squareButtonFlag : boolean = false;
var rectButtonFlag : boolean = false;
var lineButtonFlag : boolean = false;
var triangleButtonFlag : boolean = false;
var ellipseButtonFlag : boolean = false;

var removeButtonFlag : boolean = false;
var moveButtonFlag : boolean = false;
var copyButtonFlag : boolean = false;
var resizeButtonFlag : boolean = false;

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
        canvasArea.set(ID, area);
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
        canvasArea.set(ID, area);
        area = null;
        break;
        case "line":
          area.moveTo(x, y);
          area.lineTo(width,  height);
          area.closePath;
          canvasGlobal.beginPath();
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.moveTo(x, y);
          canvasGlobal.lineTo(width, height);
          canvasGlobal.closePath();
          canvasGlobal.stroke();

          canvasArea.set(ID, area);
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
      create_square_flag = false;
      create_rect_flag = false;
      create_triangle_flag = false;
      create_ellipse_flag = false;
      created_circle = false;
      created_square = false;
      created_rect = false;
      created_triangle = false;
      created_ellipse = false;
      move_flag = false;
      copy_flag = false;
      remove_flag = false;
      resize_flag = false;
      var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
      var canvasGlobal = boardGlobal.getContext("2d")!;



      create_line_flag = true;
      created_line = false;




      var selectLine = false;
      boardGlobal.addEventListener("mousedown",e=>{

        if(!created_line && (draw_line != null) && lineButtonFlag){
          draw_line={
          x : e.offsetX,
          y :e.offsetY,
          width : 0,
          height : 0,
          stCo : "white",
          fiCo : "black",
          type : "line",
          is_filled : 1,
          stWi : 2,
          shapeID : get_new_ID()
          }
          selectLine = true;
          created_line = true
        }


      });

      boardGlobal.addEventListener("mousemove", e => {
        if(create_line_flag && selectLine && (draw_line != null) && lineButtonFlag && created_line){
          canvasGlobal.clearRect(0,0,1380,675);
          canvasArea.delete(draw_line.shapeID);

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

        document.getElementById("line")!.style.backgroundColor = "rgb(246, 129, 60)"
        }

      });

      if(create_line_flag){
        document.getElementById("line")!.style.backgroundColor = "rgba(47, 24, 10, 0.856)"

      }


    }

//----------------------------------------------------------------------//

  createCircle(){
    create_square_flag = false;
    create_line_flag = false;
    create_rect_flag = false;
    create_triangle_flag = false;
    create_ellipse_flag = false;

    created_square = false;
    created_line = false;
    created_rect = false;
    created_triangle = false;
    created_ellipse = false;

    move_flag = false;
    copy_flag = false;
    remove_flag = false;
    resize_flag = false;

    create_circle_flag = true;
    created_circle = false;


    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var circle : shapeBack|null;

    boardGlobal.addEventListener("mousedown", e=> {

      if(!created_circle && (circle != null) && circleButtonFlag){

        circle={
          x : e.offsetX,
          y :e.offsetY,
          width : 30,
          height : 30,
          stCo : "white",
          fiCo : "red",
          type : "circle",
          is_filled : 1,
          stWi : 2,
          shapeID : get_new_ID()
          }
        create_circle_flag = false;
        created_circle = true;
        this.drawShape(circle, "");
        shapesBack.push(circle);
        circle = null
        }

    });


    boardGlobal.addEventListener("mouseup",e=>{
      if(circleButtonFlag){
        created_circle = true;
        create_circle_flag = false;
        circle = null;

        document.getElementById("circle")!.style.backgroundColor = "rgb(246, 129, 60)"

      }

    });
    if(create_circle_flag){
      document.getElementById("circle")!.style.backgroundColor = "rgba(47, 24, 10, 0.856)"

    }
}

//----------------------------------------------------------------------//

  createRect(){
    create_square_flag = false;
    create_line_flag = false;
    create_circle_flag = false;
    create_triangle_flag = false;
    create_ellipse_flag = false;

    created_square = false;
    created_line = false;
    created_circle = false;
    created_triangle = false;
    created_ellipse = false;

    move_flag = false;
    copy_flag = false;
    remove_flag = false;
    resize_flag = false;

    create_rect_flag = true;
    created_rect = false;


    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var rect : shapeBack|null;

    boardGlobal.addEventListener("mousedown",e=>{

      if(!created_rect && (rect != null) && rectButtonFlag){

        rect={
          x : e.offsetX,
          y :e.offsetY,
          width : 60,
          height : 30,
          stCo : "white",
          fiCo : "yellow",
          type : "circle",
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

        document.getElementById("rect")!.style.backgroundColor = "rgb(246, 129, 60)"

      }

    });
    if(create_rect_flag){
      document.getElementById("rect")!.style.backgroundColor = "rgba(47, 24, 10, 0.856)"

    }
  }

//----------------------------------------------------------------------//


  clearAll(){
    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;
    canvasGlobal.clearRect(0,0,1380,675)
    canvasArea.clear()
    shapesBack = []
  }

  //----------------------------------------------------------------------//

  disableButtons(){
    if(create_line_flag){

      circleButtonFlag = false;
      squareButtonFlag  = false;
      rectButtonFlag  = false;
      triangleButtonFlag  = false;
      ellipseButtonFlag  = false;

      moveButtonFlag = false;
      resizeButtonFlag = false;
      copyButtonFlag = false;
      removeButtonFlag = false;

      lineButtonFlag = true;



    }
    if(create_square_flag){

      circleButtonFlag = false;
      rectButtonFlag  = false;
      lineButtonFlag  = false;
      triangleButtonFlag  = false;
      ellipseButtonFlag  = false;

      moveButtonFlag = false;
      resizeButtonFlag = false;
      copyButtonFlag = false;
      removeButtonFlag = false;

      squareButtonFlag = true;
      draw_line = null;

    }
    if(create_circle_flag){



      squareButtonFlag = false;
      rectButtonFlag = false;
      lineButtonFlag = false;
      triangleButtonFlag = false;
      ellipseButtonFlag = false;
      moveButtonFlag = false;
      resizeButtonFlag = false;
      copyButtonFlag = false;
      removeButtonFlag = false;

      circleButtonFlag = true;

      draw_line = null;


    }
    if(create_rect_flag){

      circleButtonFlag  = false;
      squareButtonFlag = false;
      lineButtonFlag = false;
      triangleButtonFlag = false;
      ellipseButtonFlag = false;
      moveButtonFlag = false;
      resizeButtonFlag = false;
      copyButtonFlag = false;
      removeButtonFlag = false;

      rectButtonFlag = true;
      draw_line = null;


    }
    if(create_triangle_flag){

      circleButtonFlag = false;
      squareButtonFlag = false;
      rectButtonFlag  = false;
      lineButtonFlag = false;
      ellipseButtonFlag = false;
      moveButtonFlag = false;
      resizeButtonFlag = false;
      copyButtonFlag = false;
      removeButtonFlag = false;

      triangleButtonFlag = true;
      draw_line = null;



    }
    if(create_ellipse_flag){


      circleButtonFlag = false;
      squareButtonFlag = false;
      rectButtonFlag = false;
      lineButtonFlag = false;
      triangleButtonFlag = false;

      moveButtonFlag = false;
      resizeButtonFlag = false;
      copyButtonFlag = false;
      removeButtonFlag = false;

      draw_line = null;



      ellipseButtonFlag = true;

    }

    if(move_flag){
      circleButtonFlag = false;
      squareButtonFlag = false;
      rectButtonFlag = false;
      lineButtonFlag = false;
      triangleButtonFlag = false;
      ellipseButtonFlag = false;

      draw_line = null;

      removeButtonFlag = false;
      resizeButtonFlag = false;
      copyButtonFlag = false;

      moveButtonFlag = true;



      ellipseButtonFlag = true;
    }
    if(remove_flag){
      circleButtonFlag = false;
      squareButtonFlag = false;
      rectButtonFlag = false;
      lineButtonFlag = false;
      triangleButtonFlag = false;
      ellipseButtonFlag = false;

      draw_line = null;
      moveButtonFlag = false;
      resizeButtonFlag = false;
      copyButtonFlag = false;


      removeButtonFlag = true;


    }
    if(copy_flag){
      circleButtonFlag = false;
      squareButtonFlag = false;
      rectButtonFlag = false;
      lineButtonFlag = false;
      triangleButtonFlag = false;
      ellipseButtonFlag = false;
      draw_line = null;

      moveButtonFlag = false;
      resizeButtonFlag = false;
      removeButtonFlag = false;

      copyButtonFlag = true;



    }
    if(resize_flag){
      circleButtonFlag = false;
      squareButtonFlag = false;
      rectButtonFlag = false;
      lineButtonFlag = false;
      triangleButtonFlag = false;
      ellipseButtonFlag = false;

      moveButtonFlag = false;
      copyButtonFlag = false;
      removeButtonFlag = false;

      draw_line = null;

      resizeButtonFlag = true;


    }

    if(!create_square_flag){
      document.getElementById("square")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!create_rect_flag){
      document.getElementById("rect")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!create_circle_flag){
      document.getElementById("circle")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!create_line_flag){
      document.getElementById("line")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!create_ellipse_flag){
      document.getElementById("ellipse")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!create_triangle_flag){
      document.getElementById("triangle")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!move_flag){
      document.getElementById("move")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!copy_flag){
      document.getElementById("copy")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!resize_flag){
      document.getElementById("resize")!.style.backgroundColor = "rgb(246, 129, 60)"

    }
    if(!remove_flag){
      document.getElementById("remove")!.style.backgroundColor = "rgb(246, 129, 60)"

    }

  }



}
//----------------------------------------------------------------------//

