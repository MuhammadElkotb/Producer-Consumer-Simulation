import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import productionNetworkElement from './productionNetworkElement';
@Injectable()
export class HomeService {

  constructor(private http:HttpClient) { }
  public  generateNetwork(productionNetwork:Map<productionNetworkElement,productionNetworkElement>[]):Observable<string>{
    return this.http.post("http://localhost:8080/generateNetwork",{body: productionNetwork},{responseType:"text"})
  }

}
