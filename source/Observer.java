// Author:   Jose Santos
// FileName: Observer.java

//******************************************************************************************************
/** This is an Observer interface that allows for an Observer object to observe
  * a Subject. Note that when a change is made to the Subject, the Subject calls the
  * Observer's update() method, whereby, the Observer object is updated appropriately.
  */
//******************************************************************************************************
public interface Observer
{
	//**************************************************************************************************
	/** Updates this Observer whenever a change occurs in the Subject.
	  */
	//**************************************************************************************************
	public void update();
}