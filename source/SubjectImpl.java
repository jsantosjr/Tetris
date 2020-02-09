// Name:     Jose Santos
// FileName: SubjectImpl.java

import java.util.*;

//******************************************************************************************************
/** This class implements the methods found in the Subject interface. Note that this class contains a
  * collection of Observer objects that can and will be observing it. Users are allowed to attach
  * and detach Observer objects from this list. Once attached to it, these Observer objects are notified
  * when a change occurs in the Subject.
  */
//******************************************************************************************************
public class SubjectImpl implements Subject
{
	// the list of Observer objects
	private List<Observer> m_observers;

	//***************************************************************************************************
	/** Constructs a SubjectImp object that will take care of implementing the methods found in the
	  * Subject interface.
	  */
	//**************************************************************************************************
	public SubjectImpl()
	{
		// create the list that will hold the Observers
		m_observers = new ArrayList<Observer>();
	}

	//**************************************************************************************************
	/** Attaches an Observer to the Subject.
	  * @param observer the Observer that will be attached
	  */
	//**************************************************************************************************
	public void attach(Observer observer)
	{
		// attach the Observer
		m_observers.add(observer);
	}

	//**************************************************************************************************
	/** Detaches an Observer from the Subject.
	  * @param observer the Observer that will detached
	  */
	//**************************************************************************************************
	public void detach(Observer observer)
	{
		// detach the Observer
		m_observers.remove(observer);
	}

	//**************************************************************************************************
	/** Notifies all the Observers that are observing the Subject.
	  */
	//**************************************************************************************************
	public void notifyObservers()
	{
		for (int i = 0; i < m_observers.size(); i++)
		{
			// get an existing Observer
			Observer observer = m_observers.get(i);

			// have the Observer update itself
			observer.update();
		}
	}
}