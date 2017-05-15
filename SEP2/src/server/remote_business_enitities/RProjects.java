package server.remote_business_enitities;

import shared.MessageHeaders;
import shared.UpdateMessage;
import shared.business_entities.BusinessEntity;
import shared.business_entities.Project;
import shared.business_entities.ProjectInterface;
import shared.remote_business_interfaces.RemoteProjectInterface;
import shared.remote_business_interfaces.RemoteProjectsInterface;
import utility.observer.RemoteObserver;
import utility.observer.RemoteSubjectDelegate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by lenovo on 4/12/2017.
 */
public class RProjects implements RemoteProjectsInterface {
    //private static RemoteProjectsInterface mirror;
    private static RemoteSubjectDelegate<UpdateMessage> remoteSubjectDelegate;
    private ArrayList<RemoteProjectInterface> remoteProjects;
    private String name;


    public RProjects(shared.business_entities.Projects projects) throws RemoteException  {
        this.remoteProjects = new ArrayList<>();
        this.name = projects.getName();
        ArrayList<ProjectInterface> projectList = projects.getProjectList();
        for(ProjectInterface project: projectList){
            this.remoteProjects.add(new RProject(project));
        }
        UnicastRemoteObject.exportObject(this,0);
        //mirror = this;

    }

    public RProjects() throws RemoteException{
        remoteSubjectDelegate = new RemoteSubjectDelegate<>(this);
        this.remoteProjects = new ArrayList<>();
        UnicastRemoteObject.exportObject(this,0);
        //mirror = this;
    }

    static void notifyObservers(String messageHeader, BusinessEntity entity) throws RemoteException{
        remoteSubjectDelegate.notifyObservers(new UpdateMessage(messageHeader, entity));
    }

    @Override
    public ArrayList<RemoteProjectInterface> getRemoteProjects() throws RemoteException {
        return remoteProjects;
    }

    @Override
    public void setRemoteProjects(ArrayList<RemoteProjectInterface> remoteProjects) throws RemoteException {
        this.remoteProjects = remoteProjects;
    }

    @Override
    public void addProject(RemoteProjectInterface project) throws RemoteException {
        this.remoteProjects.add(project);
        RProjects.notifyObservers(MessageHeaders.CREATE, new Project(project));
    }

    @Override
    public RemoteProjectInterface getProject(int projectIndex) throws RemoteException {
        return this.remoteProjects.get(projectIndex);
    }

    @Override
    public RemoteProjectInterface getProject(String projectName) throws RemoteException {
        for(RemoteProjectInterface remoteProject: remoteProjects){
            if(remoteProject.getName().equals(projectName))
                return remoteProject;
        }

        return null;
    }

    @Override
    public ArrayList<String> getProjectNames() throws RemoteException{
        ArrayList<String> projectNames = new ArrayList<>();
        for(RemoteProjectInterface project: remoteProjects){
            projectNames.add(project.getName());
        }

        return projectNames;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void setName(String name) throws RemoteException {
        this.name = name;
        //RProjects.notifyObservers(this);
    }

    @Override
    public void addObserver(RemoteObserver<UpdateMessage> remoteObserver) throws RemoteException {
        remoteSubjectDelegate.addObserver(remoteObserver);
    }

    @Override
    public void deleteObserver(RemoteObserver<UpdateMessage> remoteObserver) throws RemoteException {
        remoteSubjectDelegate.deleteObserver(remoteObserver);
    }

    /*public static RemoteProjectsInterface getMirror(){
        return mirror;
    }*/
}