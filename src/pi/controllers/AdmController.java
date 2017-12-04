package pi.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pi.dao.AdmDAO;
import pi.javabean.Adm;

public class AdmController implements Initializable {

	@FXML
	private TextField user;
	@FXML
	private TextField senha;
	@FXML
	private ComboBox<String> niveldeAcesso;
	@FXML
	private TableView<Adm> tabela;
	@FXML
	private TableColumn<Adm, String> usuario;
	@FXML
	private TableColumn<Adm, String> nivelacesso;
	@FXML
	private TableColumn<Adm, Integer> id;

	ObservableList<Adm> dados;
	int idguarda;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ArrayList<Adm> lista = null;
		lista = AdmDAO.listar();
		dados = FXCollections.observableArrayList(lista);
		usuario.setCellValueFactory(new PropertyValueFactory<>("email"));
		nivelacesso.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		tabela.setItems(dados);

		niveldeAcesso.getItems().addAll("Administrador", "Operador");
		niveldeAcesso.getSelectionModel().selectFirst();

	}

	@FXML
	private void cadastrarUsuario(ActionEvent event) {
		Adm usuario = new Adm();

		usuario.setEmail(user.getText());
		usuario.setSenha(senha.getText());

		if (niveldeAcesso.getSelectionModel().getSelectedItem().equals("Administrador")) {
			usuario.setNivel("Administrador");
		}
		else if (niveldeAcesso.getSelectionModel().getSelectedItem().equals("Operador")) {
			usuario.setNivel("Operador");
		}

		try {
			AdmDAO.cadastrar(usuario);
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setContentText("Cadastro efetuado");
			alerta.showAndWait();
			limpar();
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Erro ao cadastrar");
			alerta.showAndWait();
			System.out.println(e);
		}
		dados.setAll(AdmDAO.listar());
	}

	void limpar() {
		user.clear();
		senha.clear();
	}

	@FXML
	private void editar(ActionEvent event) {
		Adm a = new Adm();
		a.setEmail(user.getText());
		a.setSenha(senha.getText());
		a.setNivel(niveldeAcesso.getValue());
		a.setId(idguarda);
		AdmDAO.editar(a);
		dados.setAll(AdmDAO.listar());
	}

	@FXML
	private void deletar(ActionEvent event) {
		Adm a = new Adm();
		a.setId(idguarda);
		AdmDAO.remover(a);
		dados.setAll(AdmDAO.listar());
	}

	@FXML
	private void selecionar(MouseEvent event) {
		if (event.getClickCount() == 2) {
			user.setText(tabela.getSelectionModel().getSelectedItem().getEmail());
			niveldeAcesso.setValue(tabela.getSelectionModel().getSelectedItem().getNivel());
			idguarda = tabela.getSelectionModel().getSelectedItem().getId();
		}
	}
}
