import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { EnderecoDTO } from '../../models/endereco.dto';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource } from '@angular/material/table';
import { debounceTime } from 'rxjs/operators';
import { ClienteService } from '../../../services/cliente-service.service';

@Component({
  selector: 'app-modal-cliente',
  templateUrl: './modal-cliente.component.html',
  styleUrls: ['./modal-cliente.component.css'],
  providers: [ClienteService],
})
export class ModalClienteComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<ModalClienteComponent>,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private clienteService: ClienteService
  ) {}

  form!: FormGroup;
  formEndereco!: FormGroup;
  isFormAtivo: Boolean = false;
  enderecos: EnderecoDTO[] = [];
  enderecoColumns: string[] = [
    'logradouro',
    'numero',
    'bairro',
    'cidade',
    'estado',
    'acao',
  ];
  dataSource!: MatTableDataSource<EnderecoDTO>;

  ngOnInit() {
    this.form = this.formBuilder.group({
      codCliente: [''],
      nome: ['', Validators.required],
      cnpj: ['', Validators.required],
    });
    this.formEndereco = this.formBuilder.group({
      cep: ['', Validators.required],
      logradouro: ['', Validators.required],
      numero: ['', Validators.required],
      bairro: ['', Validators.required],
      cidade: ['', Validators.required],
      estado: ['', Validators.required],
      latitude: [''],
      longitude: [''],
    });

    const cepControl = this.formEndereco.get('cep');
    if (cepControl) {
      cepControl.valueChanges
        .pipe(debounceTime(10))
        .subscribe((value: string) => {
          if (value.length === 8) {
            this.buscarEnderecoPorCep(value);
          }
        });
    }
  }

  buscarEnderecoPorCep(cep: String) {
    if (cep) {
      const url = `https://viacep.com.br/ws/${cep}/json`;
      this.http.get(url).subscribe((response: any) => {
        this.formEndereco.setValue({
          cep: response.cep,
          logradouro: '',
          numero: '',
          bairro: response.bairro,
          cidade: response.localidade,
          estado: response.uf,
          latitude: 0,
          longitude: 0,
        });
      });
    }
  }

  montarDTOEndereco() {
    var endereco: EnderecoDTO = {
      logradouro: this.formEndereco.get('logradouro')?.value,
      numero: this.formEndereco.get('numero')?.value,
      bairro: this.formEndereco.get('bairro')?.value,
      cidade: this.formEndereco.get('cidade')?.value,
      estado: this.formEndereco.get('estado')?.value,
      latitude: this.formEndereco.get('latitude')?.value,
      longitude: this.formEndereco.get('longitude')?.value,
      cep: this.formEndereco.get('cep')?.value,
    };
    return endereco;
  }

  salvar() {
    if (this.form.valid && this.enderecos.length > 0) {
      const clienteDTO = {
        codCliente: this.form.get('codCliente')?.value,
        nome: this.form.get('nome')?.value,
        cnpj: this.form.get('cnpj')?.value,
        enderecos: this.enderecos,
      };
      this.clienteService
        .salvarCliente(clienteDTO)
        .subscribe((response: any) => {
          this.fecharDialog();
        });
    }
  }

  fecharDialog() {
    this.dialogRef.close();
  }

  fecharFrom() {
    this.formEndereco = this.formBuilder.group({
      cep: ['', Validators.required],
      logradouro: ['', Validators.required],
      numero: ['', Validators.required],
      bairro: ['', Validators.required],
      cidade: ['', Validators.required],
      estado: ['', Validators.required],
      latitude: [''],
      longitude: [''],
    });
    const cepControl = this.formEndereco.get('cep');
    if (cepControl) {
      cepControl.valueChanges
        .pipe(debounceTime(10))
        .subscribe((value: string) => {
          if (value.length === 8) {
            this.buscarEnderecoPorCep(value);
          }
        });
    }
    this.isFormAtivo = false;
  }

  removerEndereco(endereco: EnderecoDTO): void {
    const index = this.enderecos.indexOf(endereco);
    if (index >= 0) {
      this.enderecos.splice(index, 1);
      this.dataSource.data = this.enderecos;
    }
  }

  addEndereco() {
    if (this.formEndereco.valid) {
      const enderecoDTO = this.montarDTOEndereco();
      this.enderecos.push(enderecoDTO);
      this.dataSource = new MatTableDataSource(this.enderecos);
      this.fecharFrom();
    }
  }
}
