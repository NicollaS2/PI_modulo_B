package pi.controllers;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import pi.dao.BoletoDAO;
import pi.javabean.CadastrarCliente;

public class BoletoController implements Initializable {

	@FXML
	private TableView<CadastrarCliente> tabela;
	@FXML
	private TextField codigo;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> colunaNome;
	@FXML
	private TableColumn<CadastrarCliente, String> colunaData;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> colunaValor;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> colunaParcelas;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> colunaDescricao;
	@FXML
	private ListView<String> listaDatas;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> numeros;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> colFone;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> colCelular;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> email;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> colPagos;
	@FXML
	private TableColumn<CadastrarCliente, CadastrarCliente> colStatus;
	@FXML
	private ChoiceBox<Integer> listadeParcelas;

	ObservableList<String> dadosClientes;
	ObservableList<CadastrarCliente> dadosData;
	ObservableList<Integer> inttoInteger;
	@FXML
	private BorderPane root;
	@FXML
	private CheckBox checkpagou;
	@FXML
	private Button guardarBtn;

	int parcelas, parcelas_pagas;

	public static String valor;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			// Lista codigo de contratos no choicebox
			ArrayList<String> informacoes = null;
			informacoes = BoletoDAO.listarContratos();
			dadosClientes = FXCollections.observableArrayList(informacoes);
			//codigo.setItems(dadosClientes);

		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Erro" + e);
			alerta.setHeaderText("Erro ao executar o initialize, Boleto");
			alerta.showAndWait();
		}

		System.out.println("o valor passado é "+valor);
		codigo.setText(valor);
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@FXML
	private void procurar(ActionEvent event) {
		//botão procurar seta os dados na tabela
		root.getStylesheets().removeAll("pi/layout/estilo3.css");
		root.getStylesheets().removeAll("pi/layout/estilo2.css");
		checkpagou.setSelected(false);
		listadeParcelas.setDisable(false);
		guardarBtn.setDisable(false);
		listaDatas.getItems().clear();
		ArrayList<CadastrarCliente> lista = null;
		String info = codigo.getText();
		CadastrarCliente c = new CadastrarCliente();
		c.setCodcontrato(info);

		try {
			lista = BoletoDAO.listardata(c);
			dadosData = FXCollections.observableArrayList(lista);
			colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
			colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
			colunaParcelas.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
			colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaopagto"));
			colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
			colCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
			email.setCellValueFactory(new PropertyValueFactory<>("email"));
			colPagos.setCellValueFactory(new PropertyValueFactory<>("parcelas_pagas"));
			colStatus.setCellValueFactory(new PropertyValueFactory<>("estado"));
			tabela.setItems(dadosData);

			//pega a data
			tabela.getSelectionModel().select(0, colunaData);
			Date data = tabela.getSelectionModel().getSelectedItem().getData();
			//pega o status
			tabela.getSelectionModel().select(0, colStatus);
			String sta = tabela.getSelectionModel().getSelectedItem().getEstado();
			//pega as parcelas
			tabela.getSelectionModel().select(0, colunaParcelas);
			parcelas = tabela.getSelectionModel().getSelectedItem().getParcelas();
			//pega o tipo de pagto
			tabela.getSelectionModel().select(0, colunaDescricao);
			String tipopagto = tabela.getSelectionModel().getSelectedItem().getDescricaopagto();
			//pega parcelas pagas
			tabela.getSelectionModel().select(0, colPagos);
			parcelas_pagas = tabela.getSelectionModel().getSelectedItem().getParcelas_pagas();

			int cobranca = 0;
			// de acordo com o tipo de pagamento as datas no looping são acrescentadas de formas diferentes
			switch (tipopagto) {
				case "Mensal":
					// seta 31 dias
					cobranca = 1;
					break;
				case "Trimestral":
					// seta 90 dias
					cobranca = 3;
					break;
				default:
					// seta 366 dias
					cobranca = 12;
					break;
			}

			//convertendo datas
			DateFormat newf = new SimpleDateFormat("dd-MM-yyyy");
			String dataFormatada = newf.format(data);

			SimpleDateFormat conversor = new SimpleDateFormat("dd-MM-yyyy");
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(conversor.parse(dataFormatada));

			//array de inteiros para setar no choicebox de parcelas pagas
			ArrayList<Integer> arrayint = new ArrayList<>();

			// adicionando no array
			for (int i = 0; i < parcelas; i++) {
				arrayint.add(i);
			}
			arrayint.add(parcelas);
			inttoInteger = FXCollections.observableArrayList(arrayint);
			listadeParcelas.setItems(inttoInteger);
			listadeParcelas.getSelectionModel().select(parcelas_pagas);

			// quantidade restante parcelas
			int qtdpagos = parcelas - parcelas_pagas;

			//looping para gerar datas futuras no listview
			for (int i = 1; i < parcelas + 1; i++) {
				calendario.add(Calendar.MONTH, cobranca); // Adiciona dia (cobranca) ao calendário
				dataFormatada = conversor.format(calendario.getTime()); // dt converter a data em string novamente.
				if (qtdpagos < parcelas) {
					listaDatas.getItems().add("Parcela: " + i + "° - " + dataFormatada + "- Pago");
					qtdpagos++;
				} else {
					listaDatas.getItems().add("Parcela: " + i + "° - " + dataFormatada);
				}
			}

			switch (sta) {
				case "Bloqueado":
					root.getStylesheets().add("pi/layout/estilo2.css");
					listadeParcelas.setDisable(true);
					guardarBtn.setDisable(true);
					checkpagou.setSelected(true);
					listar();
					break;
				case "Finalizado":
					root.getStylesheets().removeAll();
					root.getStylesheets().add("pi/layout/estilo3.css");
					break;
				default:
					root.getStylesheets().removeAll();
					root.getStylesheets().add("pi/layout/estilo.css");
					break;
			}

			if (parcelas_pagas == parcelas) {
				CadastrarCliente pagos = new CadastrarCliente();
				pagos.setEstado("Finalizado");
				pagos.setCodcontrato(codigo.getText());
				// da update no BD relacionando ao codigo de contrato
				BoletoDAO.atualizarStatus(pagos);
				listadeParcelas.setDisable(true);
				guardarBtn.setDisable(true);
				checkpagou.setDisable(true);
				root.getStylesheets().removeAll();
				root.getStylesheets().add("pi/layout/estilo3.css");
			} else {
				checkpagou.setDisable(false);
				root.getStylesheets().removeAll();
				root.getStylesheets().add("pi/layout/estilo.css");
			}

		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setHeaderText("Error");
			alerta.setContentText("Erro ao consultar contrato, verifique se foi digitado corretamente! \n" + e);
			alerta.showAndWait();
		}

	}

	public void listar() {
		ArrayList<CadastrarCliente> lista = null;
		String info = codigo.getText();
		CadastrarCliente c = new CadastrarCliente();
		c.setCodcontrato(info);

		lista = BoletoDAO.listardata(c);
		dadosData = FXCollections.observableArrayList(lista);
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
		colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		colunaParcelas.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaopagto"));
		colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
		colCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		colPagos.setCellValueFactory(new PropertyValueFactory<>("parcelas_pagas"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("estado"));
		tabela.setItems(dadosData);
	}

	@FXML
	private void GuardarPagos(ActionEvent event) {
		// pega o valor de parcelas selcionado no choicebox
		int valor = listadeParcelas.getSelectionModel().getSelectedItem();
		CadastrarCliente pagos = new CadastrarCliente();
		pagos.setParcelas_pagas(valor);
		pagos.setCodcontrato(codigo.getText());
		// da update no BD relacionando ao codigo de contrato
		BoletoDAO.atualizarPagos(pagos);
		procurar(event);

		if (parcelas_pagas == parcelas) {
			CadastrarCliente fina = new CadastrarCliente();
			fina.setEstado("Finalizado");
			fina.setCodcontrato(codigo.getText());
			// da update no BD relacionando ao codigo de contrato
			BoletoDAO.atualizarStatus(fina);
			listadeParcelas.setDisable(true);
			guardarBtn.setDisable(true);
			checkpagou.setDisable(true);
			listar();
			root.getStylesheets().removeAll();
			root.getStylesheets().add("pi/layout/estilo3.css");
		} else {
			checkpagou.setDisable(false);
			root.getStylesheets().removeAll();
			root.getStylesheets().add("pi/layout/estilo.css");
		}
	}

	@FXML
	private void naoPagou(MouseEvent event) {
		if (checkpagou.isSelected()) {
			root.getStylesheets().add("pi/layout/estilo2.css");
			CadastrarCliente pagos = new CadastrarCliente();
			pagos.setEstado("Bloqueado");
			pagos.setCodcontrato(codigo.getText());
			// da update no BD relacionando ao codigo de contrato
			BoletoDAO.atualizarStatus(pagos);
			listadeParcelas.setDisable(true);
			guardarBtn.setDisable(true);
			listar();
		} else if (checkpagou.isSelected() == false) {
			root.getStylesheets().removeAll("pi/layout/estilo2.css");
			root.getStylesheets().add("pi/layout/estilo.css");
			listadeParcelas.setDisable(false);
			guardarBtn.setDisable(false);
			CadastrarCliente pagos = new CadastrarCliente();
			pagos.setEstado("Regular");
			pagos.setCodcontrato(codigo.getText());
			// da update no BD relacionando ao codigo de contrato
			BoletoDAO.atualizarStatus(pagos);
			listar();
		}

	}

}
