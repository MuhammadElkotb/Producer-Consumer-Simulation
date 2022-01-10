import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import productionNetworkElement from './productionNetworkElement';
@Injectable()
export class HomeService {

  constructor(private http:HttpClient) { }
  public generateNetwork(productionNetwork : string):Observable<string>{
    return this.http.post("http://localhost:8080/generateNetwork", productionNetwork,{responseType:"text"})
  }

  public play() : Observable<Object[]>{
    return this.http.get<Object[]>("http://localhost:8080/play")
  }
  public getBuffer() : Observable<string>{

    return this.http.get("http://localhost:8080/getBuffer", {responseType:"text"});
    
  }

}
