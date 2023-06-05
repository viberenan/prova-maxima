import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ClienteService } from '../../services/cliente-service.service';
import { ClienteDTO } from '../models/cliente.dto';
import { Page } from '../models/page.model';
import { MatDialog } from '@angular/material/dialog';
import { ModalClienteComponent } from '../modais/modal-cliente/modal-cliente.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [ClienteService],
})
export class HomeComponent implements OnInit {
  constructor(
    private clienteService: ClienteService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.buscarClientes();
  }

  clientes: ClienteDTO[] = [];
  displayedColumns: string[] = ['id', 'codCliente', 'nome', 'cnpj', 'acoes'];
  pageSize: number = 10;
  page: number = 0;
  pageSizeOptions: number[] = [5, 10, 25, 100];
  totalClientes: number = 0;
  isLoading = false;
  nome: string = '';
  cnpj: string = '';
  codCliente: string = '';

  buscarClientes() {
    this.isLoading = true;
    const nome = this.nome;
    const codCliente = this.codCliente;
    const cnpj = this.cnpj;
    const pageable = {
      page: this.page,
      size: this.pageSize,
      sort: [],
    };
    this.clienteService
      .buscarClientesComFiltro(nome, codCliente, cnpj, pageable)
      .subscribe(async (response: Page<ClienteDTO>) => {
        await (this.clientes = response.content);
        this.totalClientes = response.totalElements;
        this.isLoading = false;
      });
  }
  async buscarClientesPorPagina(event: PageEvent) {
    await (this.page = event.pageIndex);
    await (this.pageSize = event.pageSize);
    this.buscarClientes();
  }
  abrirModalSalvar() {
    const dialogRef = this.dialog.open(ModalClienteComponent, {
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.buscarClientes();
    });
  }
  excluirCliente(cliente: any) {
    this.clienteService.excluirCliente(cliente.id).subscribe((response: any) => {
      this.buscarClientes();
    });
  }
}
