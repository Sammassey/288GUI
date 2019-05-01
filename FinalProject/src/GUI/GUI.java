package GUI;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GUI extends JFrame {

	public static GUI example;
	
	private static final long serialVersionUID = 1L;

	public GUI(String title) throws FileNotFoundException {
		super(title);

		// Create dataset
		XYSeriesCollection dataset = scanDoc();

		// Create chart
		JFreeChart chart = ChartFactory.createScatterPlot(" ", "X CM", "Y CM", dataset);

		Button button = new Button("Refresh");
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseZoomable(false);
		panel.add(button);
		setContentPane(panel);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					refresh(panel);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	private void refresh(ChartPanel panel) throws FileNotFoundException
	{
		XYSeriesCollection dataset = scanDoc();
		
		JFreeChart chart = ChartFactory.createScatterPlot(" ", "X CM", "Y CM", dataset);
		
		panel.setChart(chart);
		panel.setMouseZoomable(false);
		setContentPane(panel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				example = new GUI("288 Interface");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			example.setSize(800, 400);
			example.setLocationRelativeTo(null);
			example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			example.setVisible(true);
		});
	}

	public XYSeriesCollection scanDoc() throws FileNotFoundException
	{
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		XYSeries Object = new XYSeries("Object");
		XYSeries Bot = new XYSeries("Bot");
		XYSeries Edge = new XYSeries("Edge");
		XYSeries Hole = new XYSeries("Hole");

		File file = getFile();
		Scanner s = new Scanner(file);
		
		while(s.hasNextLine())
		{
			String temp = s.nextLine();
			Scanner t = new Scanner(temp);
			System.out.println(temp);
			if((temp.contains("//")) || (temp.contains("log")))
			{
				
			}
			else
			{
				double x = 0, y = 0;
				t.useDelimiter(", *");
				if(temp.contains("O"))
				{
					t.next();
					x = Double.parseDouble(t.next());
					y = Double.parseDouble(t.next());
					Object.add(x, y);
				}
				if(temp.contains("B"))
				{
					t.next();
					x = Double.parseDouble(t.next());
					y = Double.parseDouble(t.next());
					Bot.add(x, y);
				}
				if(temp.contains("E"))
				{
					t.next();
					x = Double.parseDouble(t.next());
					y = Double.parseDouble(t.next());
					Edge.add(x, y);
				}
				if(temp.contains("H"))
				{
					t.next();
					x = Double.parseDouble(t.next());
					y = Double.parseDouble(t.next());
					Hole.add(x, y);
				}
			}
		}
		
		dataset.addSeries(Hole);
		dataset.addSeries(Edge);
		dataset.addSeries(Bot);
		dataset.addSeries(Object);
		
		return dataset;
	}
	
	public File getFile()
	{
		File file = new File("C://Users//Acer//Desktop//putty.log.txt");
		return file;
	}
}
