// Author:   Jose Santos
// FileName: Subject.java

//******************************************************************************************************
/** This is a Subject interface from which subjects will be able to inherit.
  * An Observer can be added to a Subject. Doing so allows it to be notified (by the Subject)
  * when changes have occurred in the Subject.
  */
//******************************************************************************************************
public interface Subject
{
	//**************************************************************************************************
	/** Attaches an Observer to the Subject.
	  * @param o the Observer that will be attached
	  */
	//**************************************************************************************************
	public void attach(Observer o);

	//**************************************************************************************************
	/** Detaches an Observer from the Subject.
	  * @param o the Observer that will detached
	  */
	//**************************************************************************************************
	public void detach(Observer o);

	//**************************************************************************************************
	/** Notifies all the Observers that are observing the Subject.
	  */
	//**************************************************************************************************
	public void notifyObservers();
}