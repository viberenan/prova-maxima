import { EnderecoDTO } from './endereco.dto';

export interface ClienteDTO {
  id: number;
  codCliente: string;
  nome: string;
  cnpj: string;
  enderecos: EnderecoDTO[];
}
