/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import pi.dao.RenovarDAO;
import pi.javabean.CadastrarCliente;

/**
 * FXML Controller class
 *
 * @author 2017101962
 */
public class RenovarController implements Initializable {

	@FXML
	private ComboBox<String> listaContratos;
	@FXML
	private TextField nome;
	@FXML
	private TextField pagamento;
	@FXML
	private TextField parcelas;
	@FXML
	private TextField valor;
	@FXML
	private DatePicker data;
	@FXML
	private TextField status;

	CadastrarCliente n;

	ObservableList<String> dadosClientes;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Lista codigo de contratos no choicebox
		data.setValue(LocalDate.now());
		ArrayList<String> informacoes = null;
		informacoes = RenovarDAO.listarContratos();
		dadosClientes = FXCollections.observableArrayList(informacoes);
		listaContratos.setItems(dadosClientes);
		listaContratos.getSelectionModel().selectFirst();
	}

	@FXML
	private void buscaContrato(ActionEvent event) {

			String contrato = listaContratos.getSelectionModel().getSelectedItem();
			CadastrarCliente c = new CadastrarCliente();
			c.setCodcontrato(contrato);
			n = RenovarDAO.listarCliente(c);
			nome.setText(n.getNome() + " " + n.getUltimonome());
			pagamento.setText(n.getDescricaopagto());
			valor.setText(Double.toString(n.getValor()));
			parcelas.setText(Integer.toString(n.getParcelas()));
			status.setText(n.getEstado());

	}

	@FXML
	private void renovar(ActionEvent event) {
		try{
			if(status.getText().equals("Finalizado")){
			String contrato = listaContratos.getSelectionModel().getSelectedItem();
			LocalDate nova = data.getValue();
			String novaData = nova.toString();
			CadastrarCliente c = new CadastrarCliente();
			c.setCodcontrato(contrato);
			c.setDatacontratacao(novaData);
			RenovarDAO.renovar(c);
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setContentText("Contrato Renovado!!");
			alerta.showAndWait();

			ArrayList<String> informacoes = null;
			informacoes = RenovarDAO.listarContratos();
			dadosClientes = FXCollections.observableArrayList(informacoes);
			listaContratos.setItems(dadosClientes);
			listaContratos.getSelectionModel().selectFirst();
			}
		}
		catch(NullPointerException e){
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Nenhum contrato selecionado");
			alerta.showAndWait();
		}

		nome.clear();
		pagamento.clear();
		parcelas.clear();
		valor.clear();
		status.clear();
	}

}
