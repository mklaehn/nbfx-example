/**
 * This file is part of the NBFx.
 *
 * NBFx is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation in version 2 of the License only.
 *
 * NBFx is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * NBFx. If not, see <http://www.gnu.org/licenses/>.
 *
 * The NBFx project designates this particular file as subject to the
 * "Classpath" exception as provided by the NBFx Project in the GPL Version 2
 * section of the License file that accompanied this code.
 */
package org.nbfx.examples.view;

import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

public abstract class NBFxSelectionAwareTopComponent extends TopComponent implements ExplorerManager.Provider {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private final Result<Node> lookupResult = Utilities.actionsGlobalContext().lookupResult(Node.class);
    private final LookupListener nodeListener = le -> {
        if (!NBFxSelectionAwareTopComponent.this.equals(TopComponent.getRegistry().getActivated())) {
            // no change from self
            lookupResult.allInstances()
                    .stream()
                    .findFirst()
                    .ifPresent(n -> explorerManager.setRootContext(n));
        }
    };

    @Override
    public final ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    @Override
    protected void componentOpened() {
        lookupResult.addLookupListener(nodeListener);
    }

    @Override
    protected void componentClosed() {
        lookupResult.removeLookupListener(nodeListener);
    }
}
