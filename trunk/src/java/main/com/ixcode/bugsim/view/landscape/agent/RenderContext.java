/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.agent;

import com.ixcode.bugsim.view.landscape.LandscapeView;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : Provides a place to temporarily store rendering stuff like 2D Line objects
 */
public class RenderContext {

    public void setAttribute(String name, Object value) {
        _storage.put(name, value);
    }

    public Object getAttribute(String name) {
        return _storage.get(name);
    }

    public boolean hasAttribute(String name) {
        return _storage.containsKey(name);
    }

    public void setRenderForPrint(boolean renderForPrint) {
        _renderForPrint =renderForPrint;
    }

    public boolean isRenderForPrint() {
        return _renderForPrint;
    }


    public LandscapeView getView() {
        return _view;
    }

    public void setView(LandscapeView view) {
        _view = view;
    }


    private Map _storage = new HashMap();
    private boolean _renderForPrint;
    private LandscapeView _view;
}
