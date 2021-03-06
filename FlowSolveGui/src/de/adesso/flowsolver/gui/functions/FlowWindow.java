package de.adesso.flowsolver.gui.functions;

import de.adesso.flowsolver.gui.controler.FWController;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by slinde on 12.05.2016.
 */
public class FlowWindow {
	
	private Button btnUp, btnDown, btnGenerate, btnReset, btnSolve;
	private TextField tfGridSize;
	
	private Stage        primaryStage;
	private FWController events;
	private int          heigth, wight;
	private HBox hbTop, hbBottom;
	private VBox         vbLeft;
	private FlowPane     fpNotes;
	private GridPane     center;
	private List<Button> possibleFlowNodes;

	public FlowWindow(Stage stage, FWController eventhandler, int heigth, int wight) {
		primaryStage = stage;
		events = eventhandler;
		this.heigth = heigth;
		this.wight = wight;
		
		primaryStage.setMinWidth(wight);
		primaryStage.setMinHeight(heigth);
		
		primaryStage.setTitle("Flow Solver");
	}
	
	public void init() {
		BorderPane mainLayout = new BorderPane();
		
		topBox();
		hbTop.setId("hbTop");
		hbTop.getStyleClass().add("hb");
		btnUp.setId("btnUp");
		btnUp.getStyleClass().add("UpDown");
		btnDown.setId("btnDown");
		btnDown.getStyleClass().add("UpDown");
		btnGenerate.setId("btnGenerate");
		
		leftBox();
		vbLeft.setId("vbLeft");
		
		centerBox();
		center.setId("center");
		
		bottomBox();
		hbBottom.setId("hbBottom");
		hbBottom.getStyleClass().add("hb");
		
		Scene scene = new Scene(mainLayout);
		
		btnUp.setOnAction(e -> events.increase());
		btnDown.setOnAction(e -> events.decrease());
		btnGenerate.setOnAction(e -> events.generate());
		
		btnReset.setOnAction(e -> events.reset());
		btnSolve.setOnAction(e -> events.solve(center));
		
		scene.setOnKeyReleased(e -> events.keyPressed(e));
		
		mainLayout.setTop(hbTop);
		mainLayout.setLeft(vbLeft);
		BorderPane centerLayout = new BorderPane();
		centerLayout.setCenter(center);
		centerLayout.setBottom(hbBottom);
		mainLayout.setCenter(centerLayout);

		stageSize();
		scene.getStylesheets().add("css/FWDesign.css");
		primaryStage.setScene(scene);
	}
	
	public void stageSize() {
		primaryStage.setHeight(heigth);
		primaryStage.setWidth(wight);
	}
	
	private void centerBox() {/*
		flow	1 2 3 4 5 ..  n
		1
		2
		3
		4
		5
		..
		n
		 */
		center = new GridPane();
		center.setAlignment(Pos.CENTER);
		
		generateTable();
	}
	
	public void generateTable() {
		int size = events.getGridSize();
		
		center.getChildren().clear();
		center.getColumnConstraints().clear();
		center.getRowConstraints().clear();
		
		for (int i = 0; i < size; i++) {
			center.getColumnConstraints().add(new ColumnConstraints(40));
			center.getRowConstraints().add(new RowConstraints(40));
		}
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				center.add(events.newPane(), i, j);
			}
		}
		
		events.resize();
	}
	
	private void bottomBox() {
		btnReset = new Button("Reset");
		btnSolve = new Button("Solve");
		
		hbBottom = new HBox();
		
		hbBottom.getChildren().add(btnReset);
		hbBottom.getChildren().add(btnSolve);
	}
	
	private void leftBox() {
		possibleFlowNodes = new LinkedList<>();
		
		vbLeft = new VBox();
		fpNotes = new FlowPane();
		
		generateNodes();
		
		vbLeft.getChildren().add(fpNotes);
	}
	
	public void generateNodes() {
		possibleFlowNodes.clear();
		fpNotes.getChildren().clear();
		for (int i = 0; i < events.getAmountNodes(); i++) {
			Button b1 = new Button("" + (char) ('A' + i));
			b1.setId("flowNode");
			Button b2 = new Button("" + (char) ('A' + i));
			b2.setId("flowNode");
			
			Button bb1 = new Button();
			bb1.setId("fassung");
			Button bb2 = new Button();
			bb2.setId("fassung");
			
			possibleFlowNodes.add(b1);
			possibleFlowNodes.add(b2);
			
			StackPane spstart = new StackPane();
			spstart.getChildren().add(bb1);
			spstart.getChildren().add(b1);
			
			StackPane spend = new StackPane();
			spend.getChildren().add(bb2);
			spend.getChildren().add(b2);
			
			spstart.setOnDragDropped(e -> events.dropped(e, spstart));
			spstart.setOnDragOver(e -> events.dropable(e, spstart));
			spend.setOnDragDropped(e -> events.dropped(e, spend));
			spend.setOnDragOver(e -> events.dropable(e, spend));
			
			spstart.setId("possibleNode");
			spend.setId("possibleNode");
			fpNotes.getChildren().addAll(spstart, spend);
			
		}
		for (Button b : possibleFlowNodes) b.setOnDragDetected(e -> events.dragged(e, b));
	}
	
	private void topBox() {
		tfGridSize = new TextField("5");
		btnUp = new Button("v");
		btnDown = new Button("v");
		btnGenerate = new Button("Generate Grid");
		
		hbTop = new HBox();
		HBox hbSize = new HBox();
		VBox vbUpDown = new VBox();
		
		vbUpDown.getChildren().add(btnUp);
		vbUpDown.getChildren().add(btnDown);
		
		hbSize.getChildren().add(tfGridSize);
		hbSize.getChildren().add(vbUpDown);
		
		hbTop.getChildren().add(hbSize);
		hbTop.getChildren().add(btnGenerate);
	}
	
	public void show() {
		primaryStage.show();
	}
	
	public String getGridSize() {
		return events.transferToParsableInteger(tfGridSize.getText());
	}
	
	public void setGridSize(String gridSize) {
		tfGridSize.setText(gridSize);
	}
	
	public void setWindowheigth(int heigth) {
		this.heigth = heigth;
	}
	
	public void setWindowwidth(int width) {
		this.wight = width;
	}
	
	public List<Button> getPossibleNodes() {
		return possibleFlowNodes;
	}
}