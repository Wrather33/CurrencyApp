package com.example.javafx;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
//Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));

public class CurrencyApp extends Application {
    public static HashMap<String, Valute> currencies = new HashMap<String, Valute>();
    public static String basic;
    public static String target;
    public static BigDecimal course;
    public static BigDecimal basicValue;
    public static BigDecimal targetValue;
    public static Object node;

    public static BigDecimal getBasicValue() {
        return basicValue;
    }

    public static BigDecimal getTargetValue() {
        return targetValue;
    }

    public static void setTargetValue(BigDecimal targetValue) {
        CurrencyApp.targetValue = targetValue;
        CurrencyApp.targetValue = targetValue.setScale(4, RoundingMode.HALF_EVEN);
    }

    public static void setBasicValue(BigDecimal basicValue) {
        CurrencyApp.basicValue = basicValue;
        CurrencyApp.basicValue = basicValue.setScale(4, RoundingMode.HALF_EVEN);
    }

    public static void setCourse() {
        BigDecimal bigDecimal = BigDecimal.valueOf(currencies.get(basic).getVunitrate() / currencies.get(target).getVunitrate());
        course = bigDecimal;
    }

    public static BigDecimal getCourse() {
        return course;
    }

    public static void setTarget(String target) {
        CurrencyApp.target = target;
    }

    public static String getTarget() {
        return target;
    }

    public static void setBasic(String basic) {
        CurrencyApp.basic = basic;
    }

    public static String getBasic() {
        return basic;
    }

    @Override
    public void start(Stage stage) throws IOException {
        ObservableList<String> keys = FXCollections.observableArrayList(currencies.keySet());
        Label label = new Label("Currency App");
        label.setFont(new Font("Times New Roman", 30));
        Button button = new Button();
        Image image = new Image("file:///D:/JAVAFX/JavaFX/src/main/java/com/example/javafx/exchange.png");
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        TextField input = new TextField();
        TextField output = new TextField();
        ComboBox<String> valuteIn = new ComboBox<String>(keys);
        ComboBox<String> valuteOut = new ComboBox<String>(keys);
        valuteIn.setValue(basic);
        valuteOut.setValue(target);
        input.setText(basicValue.toString());
        output.setText(targetValue.toString());

        VBox vBox = new VBox(input, valuteIn);
        vBox.setAlignment(Pos.TOP_CENTER);
        VBox vBox1 = new VBox(output, valuteOut);
        vBox1.setAlignment(Pos.TOP_CENTER);

        GridPane root = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHgrow(Priority.ALWAYS);
        column1.setHalignment(HPos.CENTER);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.CENTER);
        column2.setPercentWidth(50);
        column2.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().add(column2);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(50);
        column3.setHgrow(Priority.ALWAYS);
        column3.setHalignment(HPos.CENTER);
        root.getColumnConstraints().add(column3);


        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        root.getRowConstraints().add(row1);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(80);
        root.getRowConstraints().add(row2);
        root.getColumnConstraints().add(column1);
        root.add(label, 1, 0);
        root.add(vBox, 0, 1);
        root.add(vBox1, 2, 1);
        root.add(button, 1, 1);
        input.setOnMouseClicked(mouseEvent -> {
            node = mouseEvent.getSource();
        });
        output.setOnMouseEntered(mouseEvent -> {

            node =  mouseEvent.getSource();
        });
        input.setOnMouseEntered(mouseEvent -> {
            node = mouseEvent.getSource();
        });
        output.setOnMouseClicked(mouseEvent -> {
            node = mouseEvent.getSource();
        });
        valuteIn.setOnMouseClicked(mouseEvent -> {
            node =  mouseEvent.getSource();
        });
        valuteOut.setOnMouseClicked(mouseEvent -> {
            node = mouseEvent.getSource();
        });
        button.setOnMouseClicked(mouseEvent -> {
            node = mouseEvent.getSource();
        });
        button.setOnAction(actionEvent -> {
            node = actionEvent.getSource();
            String bas = basic;
            String tag = target;
            setTarget(bas);
            setBasic(tag);
            setCourse();
            BigDecimal basvalue = getBasicValue();
            BigDecimal tagvalue = getTargetValue();
            setBasicValue(tagvalue);
            BigDecimal result = basicValue.multiply(course);
            result = result.setScale(4, RoundingMode.HALF_EVEN);
            setTargetValue(result);
            valuteIn.setValue(getBasic());
            valuteOut.setValue(getTarget());
            input.setText(getBasicValue().toString());
            output.setText(getTargetValue().toString());
        });

        ChangeListener<String> inputListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (node.equals(input)) {
                    String str = newValue;
                    if (!basicValue.toString().equals(newValue)) {
                        basic = valuteIn.getValue();
                        target = valuteOut.getValue();
                        setCourse();
                        if (str.length() == 0) {
                            str = String.valueOf(0);
                            input.setText(String.valueOf(0));
                            setBasicValue(BigDecimal.valueOf(0));
                            output.setText(String.valueOf(0));
                            setTargetValue(BigDecimal.valueOf(0));
                        } else if (str.length() > 1 && str.charAt(0) == '0' && str.charAt(1) != '.') {
                            str = newValue.substring(1, newValue.length());
                            input.setText(str);
                        } else if (str.charAt(0) == '.') {
                            str = String.valueOf(0);
                            input.setText(String.valueOf(0));
                            setBasicValue(BigDecimal.valueOf(0));
                            output.setText(String.valueOf(0));
                            setTargetValue(BigDecimal.valueOf(0));
                        }
                        try {
                            setBasicValue(BigDecimal.valueOf(Double.parseDouble(str)));
                            BigDecimal result = basicValue.multiply(course);
                            result = result.setScale(4, RoundingMode.HALF_EVEN);
                            setTargetValue(result);
                            output.setText(String.valueOf(result));
                        } catch (NumberFormatException n) {
                            input.setText(oldValue);
                            setBasicValue(BigDecimal.valueOf(Double.parseDouble(oldValue)));

                        }

                    }
                }
            }};
        ChangeListener<String> outputListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (node.equals(output)) {
                    String str = newValue;
                    if (!targetValue.toString().equals(str)) {
                        target = valuteIn.getValue();
                        basic = valuteOut.getValue();
                        setCourse();
                        if (str.length() == 0) {
                            str = String.valueOf(0);
                            input.setText(String.valueOf(0));
                            setBasicValue(BigDecimal.valueOf(0));
                            output.setText(String.valueOf(0));
                            setTargetValue(BigDecimal.valueOf(0));
                        } else if (str.length() > 1 && str.charAt(0) == '0' && str.charAt(1) != '.') {
                            str = newValue.substring(1, newValue.length());
                            output.setText(str);
                        } else if (str.charAt(0) == '.') {
                            str = String.valueOf(0);
                            output.setText(String.valueOf(0));
                            input.setText(String.valueOf(0));
                            setTargetValue(BigDecimal.valueOf(0));
                            setBasicValue(BigDecimal.valueOf(0));
                        }
                        try {
                            setTargetValue(BigDecimal.valueOf(Double.parseDouble(str)));
                            BigDecimal result = targetValue.multiply(course);
                            result = result.setScale(4, RoundingMode.HALF_EVEN);
                            setBasicValue(result);
                            input.setText(String.valueOf(result));
                        } catch (NumberFormatException n) {
                            output.setText(oldValue);
                            setTargetValue(BigDecimal.valueOf(Double.parseDouble(oldValue)));
                        }
                    }
                }
            }
        };
        input.textProperty().addListener(inputListener);
        output.textProperty().addListener(outputListener);

        valuteIn.setOnAction(actionEvent -> {
            if(node.equals(valuteIn)) {
                if (!valuteIn.getValue().equals(valuteOut.getValue())) {
                    setBasic(valuteIn.getValue());
                    setTarget(valuteOut.getValue());
                    setCourse();
                    BigDecimal result = basicValue.multiply(getCourse());
                    result = result.setScale(4, RoundingMode.HALF_EVEN);
                    setTargetValue(result);
                    output.setText(String.valueOf(result));
                }
                else {
                    button.fire();
                }
            }
        });
        valuteOut.setOnAction(actionEvent -> {
            if(node.equals(valuteOut)) {
                if (!valuteOut.getValue().equals(valuteIn.getValue())) {
                    setBasic(valuteOut.getValue());
                    setTarget(valuteIn.getValue());
                    setCourse();
                    BigDecimal result = targetValue.multiply(getCourse());
                    result = result.setScale(4, RoundingMode.HALF_EVEN);
                    setBasicValue(result);
                    input.setText(String.valueOf(result));
                }
                else {
                    button.fire();
                }
            }
        });
        Scene scene = new Scene(root, 640, 480);
        stage.setFullScreen(true);
        stage.setTitle("Currency App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("https://www.cbr-xml-daily.ru/daily.xml");
        NodeList Elements = document.getDocumentElement().getElementsByTagName("Valute");
        for (int i = 0; i < Elements.getLength(); i++) {
            Node node = Elements.item(i);
            NodeList childNodes = node.getChildNodes();
            int numCode = 0;
            String charCode = null;
            int Nominal = 0;
            String name = null;
            double value = 0;
            double vunitrate = 0;

            for (int j = 0; j < childNodes.getLength(); j++) {
                Node childNode = childNodes.item(j);
                if (childNode.getNodeName().equals("NumCode")){
                    numCode = Integer.parseInt(childNode.getTextContent());
                }
                else if(childNode.getNodeName().equals("CharCode")){
                    charCode = childNode.getTextContent();
                }
                else if(childNode.getNodeName().equals("Nominal")){
                    Nominal = Integer.parseInt(childNode.getTextContent());
                }
                else if(childNode.getNodeName().equals("Name")){
                    name = childNode.getTextContent();
                }
                else if(childNode.getNodeName().equals("Value")){
                    value = Double.parseDouble(childNode.getTextContent().replace(',','.'));
                }
                else if(childNode.getNodeName().equals("VunitRate")){
                    vunitrate = Double.parseDouble(childNode.getTextContent().replace(',','.'));
                }
            }
            currencies.put(charCode, new Valute(numCode, charCode, Nominal, name, (float) value, vunitrate));
        }
        currencies.put("RUB", new Valute(0, "RUB", 1, "Российский Рубль", 1, 1));
        basic = "RUB";
        target = "USD";
        setCourse();
        setBasicValue(BigDecimal.valueOf(1));
        setTargetValue(getCourse());
        launch();
    }
}