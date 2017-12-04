package pi;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public abstract class MaskFieldUtil {
    
     public static void cpfCnpj(TextField textField) { //Método para colocar os pontos e barras no campoCpf textField
        MaskFieldUtil.maxField(textField, 18);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
        String value = textField.getText();
            if (number2.intValue() <= 14) {
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
            } else {
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1/$2");
                value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
            }
            textField.setText(value);
            MaskFieldUtil.positionCaret(textField);
        }
        );
    }
     
     public static void fone(TextField textField) {
        MaskFieldUtil.maxField(textField, 14);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
            try {
                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");//Definir que só pode ser número entre 0-9
                int num = value.length();
                value = value.replaceFirst("(\\d{2})(\\d)", "($1)$2");
                value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
                if (num > 10) {
                    value = value.replaceAll("-", "");
                    value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
                }
                textField.setText(value);
                MaskFieldUtil.positionCaret(textField);

            } catch (Exception ex) {
            }
        }); 
     }
     
     public static void cep(TextField textField){
         MaskFieldUtil.maxField(textField, 9);
         textField.lengthProperty().addListener((observableValue, number, number2) -> {
           try{
            String value = textField.getText();
            value = value.replaceAll("[^0-9]","");
            value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
            
            textField.setText(value);
            MaskFieldUtil.positionCaret(textField);
           }catch(Exception ex){
               Alert alerta = new Alert(Alert.AlertType.ERROR);
               alerta.setContentText("CEP");
               alerta.showAndWait();
           }
         });
     }
     
     public static void valor(TextField textField){
         MaskFieldUtil.maxField(textField, 6);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
            String value = textField.getText();
            value = value.replaceAll("[^0-9]", "");
            int num = value.length();
            if(num == 4){
                value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
            }
            if(num == 5){
                value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            }
            
            textField.setText(value);
            MaskFieldUtil.positionCaret(textField);
        });
     }
     
     public static void parcela(TextField textField){
         MaskFieldUtil.maxField(textField, 2);
         textField.lengthProperty().addListener((observableValue, number, number2) -> {
            String value = textField.getText();
            value = value.replaceAll("[^0-9]", "");

            textField.setText(value);
            MaskFieldUtil.positionCaret(textField);
        });
     }
     
    private static void maxField(TextField textField, Integer length) { //Vai pegar o tamanho do maxField e limitar os digitos
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null || newValue.length() > length) {
                textField.setText(oldValue);
            }
        }
        );
    }

    private static void positionCaret(TextField textField) {//Para posicionar o cursor sempre final
       Platform.runLater(() -> {
            if (textField.getText().length() != 0) {
                textField.positionCaret(textField.getText().length());
            }
        }
        );
    }
}
