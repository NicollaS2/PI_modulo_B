package pi.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import pi.MaskFieldUtil;
import pi.dao.CadastrarClienteDAO;
import pi.dao.EditarClienteDAO;
import pi.javabean.CadastrarCliente;

public class EditarClienteController implements Initializable {

    @FXML
    private TabPane janelas;
    @FXML
    private Tab aba1;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoSobrenome;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoCpfeCnpj;
    @FXML
    private ComboBox<String> contrato;
    @FXML
    private Tab aba2;
    @FXML
    private TextField campoEndereco;
    @FXML
    private TextField campoCep;
    @FXML
    private TextField campoFone;
    @FXML
    private TextField campoCel;
    @FXML
    private TextField campoBairro;
    @FXML
    private TextField campoCel2;
    @FXML
    private ComboBox<String> listaEstado;
    @FXML
    private ComboBox<String> listaCidade;
    @FXML
    private Tab aba3;
    @FXML
    private TextField campoCod;
    @FXML
    private TextField campoDominio;
    @FXML
    private TextField campoParcelas;
    @FXML
    private TextField campoValor;
    @FXML
    private ChoiceBox<String> campoPagto;
    @FXML
    private DatePicker campoData;

    ObservableList<String> dadosClientes;

    CadastrarCliente n;

    ObservableList<String> dadosEstados;
    ObservableList<String> dadosCidades;

    String cpfecnpj;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //setando data atual
        campoData.setValue(LocalDate.now());

        // Inserindo Strings com valores no tipo de pagamento
        campoPagto.getItems().addAll("Mensal", "Trimestral", "Anual");
        campoPagto.getSelectionModel().selectFirst();

        // Inserindo valores no choice box de estados.
        try {
            ArrayList<String> listaE;
            listaE = CadastrarClienteDAO.listarEstados();
            dadosEstados = FXCollections.observableArrayList(listaE);
            listaEstado.setItems(dadosEstados);


        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro" + e);
            alerta.setHeaderText("Erro ao executar o initialize, EditarController");
            alerta.showAndWait();
        }

        //listaEstado.setItems(dados);
        MaskFieldUtil.cpfCnpj(campoCpfeCnpj);
        MaskFieldUtil.fone(campoCel);
        MaskFieldUtil.fone(campoCel2);
        MaskFieldUtil.fone(campoFone);
        MaskFieldUtil.cep(campoCep);
        MaskFieldUtil.valor(campoValor);
        MaskFieldUtil.parcela(campoParcelas);

        try {
            ArrayList<String> informacoes;
            informacoes = EditarClienteDAO.listarContratos();
            dadosClientes = FXCollections.observableArrayList(informacoes);
            contrato.setItems(dadosClientes);
            contrato.getSelectionModel().selectFirst();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro" + e);
            alerta.setHeaderText("Erro ao executar o initialize, Boleto");
            alerta.showAndWait();
        }

    }

    @FXML
    private void buscarContrato(ActionEvent event) {
        String info = contrato.getSelectionModel().getSelectedItem();
        CadastrarCliente c = new CadastrarCliente();
        c.setCodcontrato(info);
        n = EditarClienteDAO.listarCliente(c);
        campoNome.setText(n.getNome());
        campoSobrenome.setText(n.getUltimonome());

        // fazer validação para saber se o select traz um cpf ou cnpj
        campoCpfeCnpj.setText(n.getCpf());
        campoCpfeCnpj.setText(n.getCnpj());

		listaEstado.getSelectionModel().select(n.getEstado());

        campoEmail.setText(n.getEmail());
        campoFone.setText(n.getFone());
        campoCel.setText(n.getCelular());
        campoCel2.setText(n.getCelular2());
        campoEndereco.setText(n.getEndereco());
        campoCep.setText(n.getCep());
        campoBairro.setText(n.getBairro());
        campoCod.setText(n.getCodcontrato());
        campoDominio.setText(n.getDominio());
        double v = n.getValor() + 00;
        int p = n.getParcelas();
        campoValor.setText(String.valueOf(v));
        campoParcelas.setText(String.valueOf(p));

    }

    @FXML
    private void next1(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = janelas.getSelectionModel();
        selectionModel.select(aba2); //select by object
        // evento para dar proximo nas abas
    }

    @FXML
    private void next2(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = janelas.getSelectionModel();
        selectionModel.select(aba3);
        // evento para dar proximo nas abas
    }

    @FXML
    private void eventoEstado(ActionEvent event) {
        String sigla = listaEstado.getSelectionModel().getSelectedItem();
        CadastrarCliente estado = new CadastrarCliente();
        estado.setEstado(sigla);
        ArrayList<String> listaC;
        listaC = CadastrarClienteDAO.listarCidades(estado);
        dadosCidades = FXCollections.observableArrayList(listaC);
        listaCidade.setItems(dadosCidades);
        listaCidade.getSelectionModel().select(n.getCidade());
    }

    @FXML
    private void salvar(ActionEvent event) {
        // implementar aqui no botão salvar o médoto para dar update

        try {
            String cpfecnpj = campoCpfeCnpj.getText();
            CadastrarCliente c = new CadastrarCliente();

            c.setNome(campoNome.getText());
            c.setUltimonome(campoSobrenome.getText());
            if (cpfecnpj.length() == 14) {
                c.setCpf(campoCpfeCnpj.getText());
            }
            if (cpfecnpj.length() == 18) {
                c.setCnpj(campoCpfeCnpj.getText());
            }
            c.setEmail(campoEmail.getText());
            c.setFone(campoFone.getText());

            LocalDate nova = campoData.getValue();
            String data = nova.toString();
            c.setDatacontratacao(data);

            c.setCelular(campoCel.getText());
            c.setCelular2(campoCel2.getText());
            c.setEndereco(campoEndereco.getText());
            c.setCep(campoCep.getText());
            c.setBairro(campoBairro.getText());
            c.setDominio(campoDominio.getText());
            c.setValor(Double.parseDouble(campoValor.getText()));
            c.setParcelas(Integer.parseInt(campoParcelas.getText()));
            c.setCodcontrato(contrato.getValue());
            c.setConcatenacaoNome(campoCod.getText());
            c.setCidade(listaCidade.getValue());
            c.setEstado(listaEstado.getValue());
            switch (campoPagto.getValue()) {
                case "Mensal":
                    c.setFormapgto(1);
                    break;
                case "Trimestral":
                    c.setFormapgto(2);
                    break;
                default:
                    c.setFormapgto(3);
                    break;
            }

            /*  Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setHeaderText("Confirma edição dos dados?");
        alerta.showAndWait();
        SingleSelectionModel<Tab> selectionModel = janelas.getSelectionModel();
        selectionModel.select(aba1);

        EditarClienteDAO.editar(c);
        limpar();*/
            if (c.getNome().equals("")
                    || c.getUltimonome().equals("")
                    //|| c.getCpf().equals("")
                    //|| c.getCnpj().equals("")
                    || c.getEmail().equals("")
                    || c.getFone().equals("")
                    || c.getCelular().equals("")
                    || c.getEndereco().equals("")
                    || c.getCep().equals("")
                    //|| c.getCidade().equals("")
                    //|| c.getEstado().equals("")
                    || c.getBairro().equals("")
                    || c.getDominio().equals("")
                    || (c.getValor() == 0)
                    || c.getDatacontratacao().equals("")
                    || (c.getParcelas() == 0)) {

                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Erro ao Editar, verifique os campos em aberto!");
                System.out.println();
                alerta.showAndWait();
            }
            else if(!campoEmail.getText().contains("@")){
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Erro ao Editar, verifique se o Email contém @");
                System.out.println();
                alerta.showAndWait();
            }
            else if(cpfecnpj.length() < 14 || cpfecnpj.length() > 14 && cpfecnpj.length() < 18){
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Erro ao Editar, verifique se o CPF ou o CNPJ estão corretos");
                System.out.println();
                alerta.showAndWait();
            }
            else if(campoCep.getText().length() < 9){
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Erro ao Editar, verifique se o CEP está correto");
                System.out.println();
                alerta.showAndWait();
            }
            else {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setContentText("Confirmar edição?");
                Optional<ButtonType> result = alerta.showAndWait();
                
                if(result.get() == ButtonType.OK){
                    EditarClienteDAO.editar(c);
                    limpar();
                }
                else{
                    
                }
                
                SingleSelectionModel<Tab> selectionModel = janelas.getSelectionModel();
                selectionModel.select(aba1);
            }

        } catch (NumberFormatException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Campos vazios ou formato de número inadequado, erro:\n\n " + e);
            alerta.showAndWait();
            System.out.println(e);
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro ao cadastrar, verifique se o codigo de contrato já é existente\n\n" + e);
            alerta.showAndWait();
            System.out.println(e);
        }

    }

    public void limpar() {
        //Evento para limpar os campos após salvar
        campoNome.clear();
        campoSobrenome.clear();
        campoEmail.clear();
        campoDominio.clear();
        campoFone.clear();
        campoCpfeCnpj.clear();
        campoCep.clear();
        campoEndereco.clear();
        campoBairro.clear();
        campoCod.clear();
        campoValor.clear();
        // campoData.clear();
        campoParcelas.clear();
    }

}
