package client.view.windows;

import client.view.internal_frames.*;
import shared.business_entities.ProjectInterface;

import javax.swing.*;

public class Root extends Window {

	private static final long serialVersionUID = 1L;
	public static AbstractJIF projectFrame;
	public static AbstractJIF sprintFrame;
	public static AbstractJIF calendarFrame;
	public static AbstractJIF chatFrame;

	public static String currentProjectName;


    @Override
    public void setEventHandlers() {

    }

    @Override
    public void initComponents() {
    	setLayout(null);
    	
		//Project frame
		projectFrame = new Projects();
		add(projectFrame);
		
		//Sprint frame
		sprintFrame = new Sprints();
		add(sprintFrame);
		
		//Calendar frame
		calendarFrame = new Calendar();
		add(calendarFrame);
		
		//Chat frame
		chatFrame = new Chat();
		add(chatFrame);
		
		//Background image
		JButton background = new JButton("");
		background.setBounds(0, 0, 2001, 1257);
		background.setContentAreaFilled(false);
		background.setBorderPainted(false);
		background.setBorder(null);
		background.setHorizontalAlignment(SwingConstants.RIGHT);
		background.setIcon(new ImageIcon(Root.class.getResource("/resources/planning_group_2000_logo.jpg")));
		background.setVerticalAlignment(SwingConstants.BOTTOM);
		add(background);

    }

    @Override
    public void loadData() {
		projectFrame.loadData(controller.getProjectsFromModel());
        if(currentProjectName == null || currentProjectName.equals("Projects")) {
			calendarFrame.clear();
			chatFrame.clear();
			return;
		}

        ProjectInterface project = controller.getProjectFromModel(currentProjectName);
        calendarFrame.loadData(project.getCalendar());
		chatFrame.loadData(project.getChat());
	}

    @Override
    public void showLogin() {
        view.setCurrentWindow(LOGIN);
    }

    @Override
    public void showRoot() {

    }
}
