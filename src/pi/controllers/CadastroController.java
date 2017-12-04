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
import pi.javabean.CadastrarCliente;

// @author Nicollas Ramires
public class CadastroController implements Initializable {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoSobrenome;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoDominio;
    @FXML
    private TextField campoFone;
    @FXML
    private TextField campoCep;
    @FXML
    private TextField campoEndereco;
    @FXML
    private TextField campoBairro;
    @FXML
    private TextField campoCod;
    @FXML
    private TextField campoValor;
    @FXML
    private DatePicker campoData;
    @FXML
    private ChoiceBox<String> campoPagto;
    @FXML
    private TextField campoParcelas;
    @FXML
    private TextField campoCel;
    @FXML
    private TextField campoCel2;
    @FXML
    private Tab aba1;
    @FXML
    private Tab aba2;
    @FXML
    private Tab aba3;
    @FXML
    private TabPane janelas;
    @FXML
    private ComboBox<String> listaEstado;
    @FXML
    private ComboBox<String> listaCidade;

    ObservableList<String> dadosEstados;
    ObservableList<String> dadosCidades;
    @FXML
    private TextField campoCpfeCnpj;

    ObservableList<CadastrarCliente> dados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //setando data atual
        campoData.setValue(LocalDate.now());

        // Inserindo Strings com valores no tipo de pagamento
        campoPagto.getItems().addAll("Mensal", "Trimestral", "Anual");
        campoPagto.getSelectionModel().selectFirst();

        // Inserindo valores no choice box de estados.
        try {
            ArrayList<String> listaE = null;
            listaE = CadastrarClienteDAO.listarEstados();
            dadosEstados = FXCollections.observableArrayList(listaE);
            listaEstado.setItems(dadosEstados);
            listaEstado.getSelectionModel().selectFirst();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Erro" + e);
            alerta.setHeaderText("Erro ao executar o initialize, CadastroController");
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
    }

    @FXML
    private void salvar(ActionEvent event) { // evento do botão salvar para cadastrar os campos no BD.

        try {
            String cpfecnpj = campoCpfeCnpj.getText();
            CadastrarCliente cliente = new CadastrarCliente();
            cliente.setNome(campoNome.getText());
            cliente.setUltimonome(campoSobrenome.getText());
            cliente.setEmail(campoEmail.getText());
            cliente.setFone(campoFone.getText());
            cliente.setCelular(campoCel.getText());
            cliente.setCelular2(campoCel2.getText());
            if (cpfecnpj.length() == 14) {
                cliente.setCpf(campoCpfeCnpj.getText());
            }
            if (cpfecnpj.length() == 18) {
                cliente.setCnpj(campoCpfeCnpj.getText());
            }
            cliente.setEndereco(campoEndereco.getText());
            cliente.setCep(campoCep.getText());
            cliente.setCidade(listaCidade.getValue());
            cliente.setEstado(listaEstado.getValue());
            cliente.setBairro(campoBairro.getText());
            cliente.setDominio(campoDominio.getText());
            cliente.setCodcontrato(campoCod.getText());
            cliente.setValor(Double.parseDouble(campoValor.getText()));
            if (campoPagto.getValue().equals("Mensal")) {
                cliente.setFormapgto(1);
            } else if (campoPagto.getValue().equals("Trimestral")) {
                cliente.setFormapgto(2);
            } else {
                cliente.setFormapgto(3);
            }
            LocalDate nova = campoData.getValue();
            String data = nova.toString();
            cliente.setDatacontratacao(data);
            cliente.setParcelas(Integer.parseInt(campoParcelas.getText()));

            if (cliente.getNome().equals("") || cliente.getEmail().equals("") || cliente.getFone().equals("")
                    || cliente.getCelular().equals("") || cliente.getEndereco().equals("") || cliente.getCep().equals("")
                    || cliente.getCidade().equals("")
                    || cliente.getEstado().equals("") || cliente.getBairro().equals("") || cliente.getDominio().equals("")
                    || (cliente.getValor() == 0) || (cliente.getCodcontrato().equals("")) || cliente.getDatacontratacao().equals("")
                    || (cliente.getParcelas() == 0)) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Erro ao Cadastrar, verifique os campos em aberto!");
                System.out.println();
                alerta.showAndWait();
            }
            else if(!campoEmail.getText().contains("@")){
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Erro ao Cadastrar, verifique se o Email contém @");
                System.out.println();
                alerta.showAndWait();
            }
            else if(cpfecnpj.length() < 14 || cpfecnpj.length() > 14 && cpfecnpj.length() < 18){
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Erro ao Cadastrar, verifique se o CPF ou o CNPJ estão corretos");
                System.out.println();
                alerta.showAndWait();
            }
            else if(campoCep.getText().length() < 9){
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setContentText("Erro ao Cadastrar, verifique se o CEP está correto");
                System.out.println();
                alerta.showAndWait();
            }
            else {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setContentText("Confirmar cadastro?");
                Optional <ButtonType> result = alerta.showAndWait();
                
                if(result.get() == ButtonType.OK){
                    CadastrarClienteDAO.cadastrar(cliente);
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

    @FXML
    private void eventoEstado(ActionEvent event) {
        String sigla = listaEstado.getSelectionModel().getSelectedItem();
        CadastrarCliente estado = new CadastrarCliente();
        estado.setEstado(sigla);
        ArrayList<String> listaC = null;
        listaC = CadastrarClienteDAO.listarCidades(estado);
        dadosCidades = FXCollections.observableArrayList(listaC);
        listaCidade.setItems(dadosCidades);
        listaCidade.getSelectionModel().selectFirst();
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
