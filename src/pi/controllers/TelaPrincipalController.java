package pi.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pi.dao.ListarTelaPrincipalDAO;
import pi.dao.LoginDAO;
import pi.javabean.CadastrarCliente;
import pi.javabean.NivelAcesso;

// @author 20171019620
public class TelaPrincipalController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	ObservableList<CadastrarCliente> dados;
	@FXML
	private TableView<CadastrarCliente> tabela;
	@FXML
	private TableColumn<CadastrarCliente, String> colData;
	@FXML
	private TableColumn<CadastrarCliente, String> colContrato;
	@FXML
	private TableColumn<CadastrarCliente, Double> colValor;
	@FXML
	private TableColumn<CadastrarCliente, String> colCpfCnpj;
	@FXML
	private TableColumn<CadastrarCliente, String> colNome;
	@FXML
	private TableColumn<CadastrarCliente, String> colRua;
	@FXML
	private TableColumn<CadastrarCliente, String> colBairro;
	@FXML
	private TableColumn<CadastrarCliente, String> colCidade;
	@FXML
	private TableColumn<CadastrarCliente, String> colEstado;
	@FXML
	private TableColumn<CadastrarCliente, String> colCep;
	@FXML
	private TableColumn<CadastrarCliente, String> colEmail;
	@FXML
	private TitledPane adm;
	@FXML
	private BorderPane raiz;
	@FXML
	private TableColumn<CadastrarCliente, String> colCpf;
	@FXML
	private TableColumn<CadastrarCliente, String> colCnpj;
	@FXML
	private TextField pegaDia;

	boolean busca;
	int dia;
	@FXML
	private Button mostrarTodos;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		busca = true;

		// chama o método para listar valores do BD no tableview
		listartabela();

		// Instancia nivelAcesso e passa parametros de email statico
		NivelAcesso n1 = new NivelAcesso();
		n1.setEmail(NivelAcesso.getEmail());

		//se o método da DAO retornar true, nivel de acesso do usuario é 2 então botão do adm fica habilitado.
		if (LoginDAO.Nivelacesso(n1) == true) {
			adm.setDisable(false);
		}
	}

	@FXML
	private void cadastro(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pi/layout/Cadastro.fxml"));
		Parent telaNova = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(telaNova));
		Screen screen = Screen.getPrimary();
		telaNova.getStylesheets().add("pi/layout/estilo.css");
		stage.setTitle("Cadastro de Clientes");
		stage.getIcons().add(new Image("pi/img/atualcommerce_novo.png"));
		stage.showAndWait();

		listartabela();
	}

	public void listartabela() {

		ArrayList<CadastrarCliente> lista = null;

		try {
			if (busca == true) {
				lista = ListarTelaPrincipalDAO.listar();
			} else {
				lista = ListarTelaPrincipalDAO.listarDia(dia);
				mostrarTodos.setDisable(false);
			}

			// todas os valores são da javabean e não do BD
			dados = FXCollections.observableArrayList(lista);
			colData.setCellValueFactory(new PropertyValueFactory<>("datacontratacao"));
			colContrato.setCellValueFactory(new PropertyValueFactory<>("codcontrato"));
			colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
			colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
			colNome.setCellValueFactory(new PropertyValueFactory<>("concatenacaoNome"));
			colRua.setCellValueFactory(new PropertyValueFactory<>("endereco"));
			colBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
			colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
			colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
			colCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
			colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			tabela.setItems(dados);

		} catch (Exception e) {

			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setHeaderText("Erro!");
			alerta.setContentText("Erro: " + e);
			alerta.showAndWait();
			//System.exit(0);
		}

		// Pegar valor das linhas e colunas da tabela com a inserção de um textfiel ao clicar
		colContrato.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colContrato.setCellFactory(TextFieldTableCell.forTableColumn());

		colCpf.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colCnpj.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colCpf.setCellFactory(TextFieldTableCell.forTableColumn());
		colCnpj.setCellFactory(TextFieldTableCell.forTableColumn());

		colNome.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colNome.setCellFactory(TextFieldTableCell.forTableColumn());

		colRua.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colRua.setCellFactory(TextFieldTableCell.forTableColumn());

		colBairro.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colBairro.setCellFactory(TextFieldTableCell.forTableColumn());

		colCidade.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colCidade.setCellFactory(TextFieldTableCell.forTableColumn());

		colEstado.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colEstado.setCellFactory(TextFieldTableCell.forTableColumn());

		colCep.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colCep.setCellFactory(TextFieldTableCell.forTableColumn());

		colEmail.setOnEditCancel((TableColumn.CellEditEvent<CadastrarCliente, String> evento) -> {
			((CadastrarCliente) evento.getTableView().getItems().get(
					evento.getTablePosition().getRow())).setNome(evento.getOldValue());
		});
		colEmail.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	@FXML
	private void verUsuarios(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pi/layout/Adm.fxml"));
		Parent telaNova = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(telaNova));
		Screen screen = Screen.getPrimary();
		telaNova.getStylesheets().add("pi/layout/estilo.css");
		stage.setTitle("Gerência de usuários");
		stage.getIcons().add(new Image("pi/img/atualcommerce_novo.png"));
		stage.showAndWait();
	}

	@FXML
	private void logout(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pi/layout/login.fxml"));
		Parent telaNova = (Parent) fxmlLoader.load();
		Stage stage = (Stage) raiz.getScene().getWindow();
		stage.setScene(new Scene(telaNova));
		Screen screen = Screen.getPrimary();
		telaNova.getStylesheets().add("pi/layout/estilo.css");
		stage.setTitle("Login no sistema...");
		stage.getIcons().add(new Image("pi/img/atualcommerce_novo.png"));
		stage.show();
	}

	@FXML
	private void sair(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	private void Consultar(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pi/layout/Boleto.fxml"));
		Parent telaNova = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(telaNova));
		Screen screen = Screen.getPrimary();
		telaNova.getStylesheets().add("pi/layout/estilo.css");
		stage.setTitle("Consulta de boletos");
		stage.getIcons().add(new Image("pi/img/atualcommerce_novo.png"));
		stage.showAndWait();
	}

	@FXML
	private void buscarDia(ActionEvent event) {
		dia = Integer.parseInt(pegaDia.getText());
		busca = false;
		listartabela();
	}

	@FXML
	private void todo(ActionEvent event) {
		busca = true;
		listartabela();
		mostrarTodos.setDisable(true);
	}

	@FXML
	private void editarCliente(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pi/layout/EditarCliente.fxml"));
		Parent telaNova = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(telaNova));
		Screen screen = Screen.getPrimary();
		telaNova.getStylesheets().add("pi/layout/estilo.css");
		stage.setTitle("Editar Cliente");
		stage.getIcons().add(new Image("pi/img/atualcommerce_novo.png"));
		stage.showAndWait();

		listartabela();

	}

	@FXML
	private void internetBanking(ActionEvent event) throws URISyntaxException, IOException {
		Desktop d = Desktop.getDesktop();
		d.browse(new URI("https://www.santander.com.br/br/pessoa-juridica"));
	}

	@FXML
	private void verBloqueados(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pi/layout/Bloqueados.fxml"));
		Parent telaNova = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(telaNova));
		Screen screen = Screen.getPrimary();
		telaNova.getStylesheets().add("pi/layout/estilo.css");
		stage.setTitle("Contratos Bloqueados");
		stage.getIcons().add(new Image("pi/img/atualcommerce_novo.png"));
		stage.showAndWait();
	}

	@FXML
	private void Renovar(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pi/layout/Renovar.fxml"));
		Parent telaNova = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(telaNova));
		Screen screen = Screen.getPrimary();
		telaNova.getStylesheets().add("pi/layout/estilo.css");
		stage.setTitle("Renovar Contrato");
		stage.getIcons().add(new Image("pi/img/atualcommerce_novo.png"));
		stage.showAndWait();
		listartabela();
	}
}
