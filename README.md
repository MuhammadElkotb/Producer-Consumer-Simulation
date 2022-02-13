# Producer/Consumer Simulation
## Table of Contents
- [Setup](#Setup)
- [Design Patterns and UML Class Diagram](#Design-Patterns-and-UML-Class-Diagram)
    - [Table of Used Design Patterns](#Table-of-Used-Design-Patterns)
    - [Design Patterns Explaination](#Design-Patterns-Explaination)
    - [UML Class Diagram](#UML-Class-Diagram)
- [Design Decisions](#Design-Decisions)
- [User Guide](#User-Guide)
- [Demo Video](#Demo-Video)
## Setup
> - First of all: you must install the `Front-End` folder which contains angular project, using npm install just because node modules may be missing. 
> - Secondly: Spring Boot folder is straight forward just open the pom file using any IDE.
> - Thirdly: you run the Angular project and on localhost:4200 and the Spring Boot project on localhost:8080.
## Design Patterns and UML Class Diagram
### Table of Used Design Patterns
|        **Design Patterns**        |
| :-------------------------------: |
|        `Singelton Pattern`        |
|         `Façade Pattern`         |
|         `Snapshot Pattern`        |
|         `Observer Pattern`        |
|     `Producer Consumer Pattern`   |

### Design Patterns Explaination
We used about five design patterns, and we make sure that we used 
them when we need them, one creational, one structural, two 
behavioral and one concurrency design patterns as follow:
> + <b>Singleton design pattern:</b> used to reduce the number of objects 
> in the project.
> + <b>Façade Design Pattern:</b> used in the network, which is used 
> mainly by the controller, to create all objects and classes.
> + <b>Snapshot Design Pattern:</b> used for our replay feature.
> + <b>Observer Design Pattern:</b> used to check the queue of 
> waiting products and to detect if a machine is ready and to 
> detect any change in the network and send it to frontend.
> + <b>Producer/consumer Design Pattern:</b> used to deal with machines and queues and control the multi-threading flow
### UML Class Diagram
![image](https://github.com/Deffo0/Producer-Consumer-Simulation/blob/main/UML%20Class%20Diagram.png)
## Design Decisions
+ We assumed the user must begin and end with a queue. 
+ The machine’s color is red when it’s ready and every product has 
a different color 
+ Every machine must have a forward and backward queue. 
+ Production process’s real time is implemented by polling 
requests from front to back and request response is null if there 
is no change in network of backend.
## User Guide
1. <b>Network Creation:</b> To create objects (Machines and 
Queues) you press on the object you want on the tools 
toolbar and then press once in the place you want the 
object to be placed in. To link two objects you press on the 
line tool and then press on the first object and drag the line 
to the second object. You can clear the Network, if you 
don’t like your created network, by pressing on the clear 
tool in the toolbar
2. <b>Network Running:</b> After creating your network you can 
press on the play button which begins the simulation, and 
you can see the number of products in each object and a 
different color for each product. If you want to stop the 
simulation, you can press on the stop button in the toolbar.
3. <b>Simulation replay:</b> If you want to replay the simulation 
then you can press on the replay button on the left toolbar, 
and the simulation will run again with everything the same 
as when the simulation was running for the first time
## Demo Video
Follow this link `https://drive.google.com/file/d/10kFN6lQ2Rq1-KNkBw34cVXdLZEGl_H7w/view`
