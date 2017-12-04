package pi.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pi.dao.ListarBloqueadosDAO;
import pi.javabean.CadastrarCliente;

public class BloqueadosController implements Initializable {

	@FXML
	private TableView<CadastrarCliente> tabela;
	@FXML
	private TableColumn<CadastrarCliente, String> contrato;
	@FXML
	private TableColumn<CadastrarCliente, String> dominio;
	@FXML
	private TableColumn<CadastrarCliente, Integer> parcelas;
	@FXML
	private TableColumn<CadastrarCliente, Integer> pagos;
	@FXML
	private TableColumn<CadastrarCliente, String> situacao;
	ObservableList<CadastrarCliente> dados;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ArrayList<CadastrarCliente> lista = null;
		lista = ListarBloqueadosDAO.listar();
		dados = FXCollections.observableArrayList(lista);
		contrato.setCellValueFactory(new PropertyValueFactory<>("codcontrato"));
		dominio.setCellValueFactory(new PropertyValueFactory<>("dominio"));
		parcelas.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
		pagos.setCellValueFactory(new PropertyValueFactory<>("parcelas_pagas"));
		situacao.setCellValueFactory(new PropertyValueFactory<>("estado"));
		tabela.setItems(dados);
	}
}
