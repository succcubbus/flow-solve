package de.adesso.flowsolver.gui;

/**
 * Created by slinde on 12.05.2016.
 */

import de.adesso.flowsolver.gui.controler.FWController;
import de.adesso.flowsolver.gui.controler.SDWController;
import de.adesso.flowsolver.gui.controler.SSWController;
import javafx.application.Application;
import javafx.stage.Stage;

public class FlowSolverGUI extends Application {
	
	private FWController fwController = new FWController();
	private SSWController sswController = new SSWController();
	private SDWController sdwController = new SDWController();
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		init(primaryStage);
	}

	private void init(Stage primaryStage) {
		fwController.init(primaryStage, this);
		fwController.show();
	}

	public void sswController() {
		sswController.init(new Stage(), this);
		sswController.show();
	}

	public void sdwController() {
		sdwController.init(new Stage(), this);
		sdwController.show();
	}

	//TODO finde heraus wie ein zweites(drittes) fenster in fx aufgemacht weden kann!!11elf
}
