import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClienteDTO } from '../Components/models/cliente.dto';
import { Page } from '../Components/models/page.model';

@Injectable()
export class ClienteService {
  apiUrl = 'http://localhost:8082/maxima/cliente';

  constructor(private http: HttpClient) {}

  buscarClientesComFiltro(
    nome: string,
    codCliente: string,
    cnpj: string,
    pageable: any
  ): Observable<Page<ClienteDTO>> {
    let params = new HttpParams();
    if (nome) {
      params = params.set('nome', nome);
    }

    if (codCliente) {
      params = params.set('cod', codCliente);
    }

    if (cnpj) {
      params = params.set('cnpj', cnpj);
    }

    params = params.set('size', pageable.size);

    params = params.set('page', pageable.page);

    return this.http.get<Page<ClienteDTO>>(this.apiUrl, { params });
  }
  salvarCliente(dto: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiUrl, dto, { headers: headers });
  }
}
