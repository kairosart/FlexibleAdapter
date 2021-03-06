/*
 * Copyright 2016 Davide Steduto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.davidea.viewholders;

import android.support.annotation.CallSuper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * ViewHolder for a Expandable Items. Holds callbacks which can be used to trigger expansion events.
 * <p>This class extends {@link FlexibleViewHolder}, which means it will benefit of all implemented
 * methods the super class holds.</p>
 *
 * @author Davide Steduto
 * @since 16/01/2016 Created
 */
public abstract class ExpandableViewHolder extends FlexibleViewHolder {

	/*--------------*/
	/* CONSTRUCTORS */
	/*--------------*/

	/**
	 * Default constructor.
	 *
	 * @param view    The {@link View} being hosted in this ViewHolder
	 * @param adapter Adapter instance of type {@link FlexibleAdapter}
	 * @since 5.0.0-b1
	 */
	public ExpandableViewHolder(View view, FlexibleAdapter adapter) {
		super(view, adapter);
	}

	/**
	 * Constructor to configure the sticky behaviour of a view.
	 * <p><b>Note:</b> StickyHeader works only if the item has been declared of type
	 * {@link eu.davidea.flexibleadapter.items.IHeader}.</p>
	 *
	 * @param view         The {@link View} being hosted in this ViewHolder
	 * @param adapter      Adapter instance of type {@link FlexibleAdapter}
	 * @param stickyHeader true if the View can be a Sticky Header, false otherwise
	 * @since 5.0.0-b7
	 */
	public ExpandableViewHolder(View view, FlexibleAdapter adapter, boolean stickyHeader) {
		super(view, adapter, stickyHeader);
	}

	/*--------------*/
	/* MAIN METHODS */
	/*--------------*/

	/**
	 * Allows to expand or collapse child views of this ItemView when {@link OnClickListener}
	 * event occurs on the entire view.
	 * <p>This method returns always true; Extend with "return false" to Not expand or collapse
	 * this ItemView onClick events.</p>
	 *
	 * @return always true, if not overridden
	 * @since 5.0.0-b1
	 */
	protected boolean isViewExpandableOnClick() {
		return true;
	}

	/**
	 * Allows to collapse child views of this ItemView when {@link OnLongClickListener}
	 * event occurs on the entire view.
	 * <p>This method returns always true; Extend with "return false" to Not collapse this
	 * ItemView onLongClick events.</p>
	 *
	 * @return always true, if not overridden
	 * @since 5.0.0-b1
	 */
	protected boolean isViewCollapsibleOnLongClick() {
		return true;
	}

	/**
	 * Expands or Collapses based on the current state.
	 *
	 * @since 5.0.0-b1
	 */
	@CallSuper
	protected void toggleExpansion() {
		int position = getFlexibleAdapterPosition();
		if (mAdapter.isExpanded(position)) {
			collapseView(position);
		} else if (!mAdapter.isSelected(position)) {
			expandView(position);
		}
	}

	/**
	 * Triggers expansion of the Item.
	 *
	 * @since 5.0.0-b1
	 */
	@CallSuper
	protected void expandView(int position) {
		mAdapter.expand(position);
	}

	/**
	 * Triggers collapse of the Item.
	 *
	 * @since 5.0.0-b1
	 */
	@CallSuper
	protected void collapseView(int position) {
		mAdapter.collapse(position);
	}

	/*---------------------------------*/
	/* CUSTOM LISTENERS IMPLEMENTATION */
	/*---------------------------------*/

	/**
	 * Called when user taps once on the ItemView.
	 * <p><b>Note:</b> In Expandable version, it tries to expand, but before,
	 * it checks if the view {@link #isViewExpandableOnClick()}.</p>
	 *
	 * @param view the View that is the trigger for expansion
	 * @since 5.0.0-b1
	 */
	@Override
	public void onClick(View view) {
		if (mAdapter.isEnabled(getFlexibleAdapterPosition()) && isViewExpandableOnClick()) {
			toggleExpansion();
		}
		super.onClick(view);
	}

	/**
	 * Called when user long taps on the ItemView.
	 * <p><b>Note:</b> In Expandable version, it tries to collapse, but before,
	 * it checks if the view {@link #isViewCollapsibleOnLongClick()}.</p>
	 *
	 * @param view the View that is the trigger for collapsing
	 * @since 5.0.0-b1
	 */
	@Override
	public boolean onLongClick(View view) {
		int position = getFlexibleAdapterPosition();
		if (mAdapter.isEnabled(position) && isViewCollapsibleOnLongClick()) {
			collapseView(position);
		}
		return super.onLongClick(view);
	}

	/**
	 * {@inheritDoc}
	 * <p><b>Note:</b> In the Expandable version, expanded items are forced to collapse.</p>
	 * @since 5.0.0-b1
	 */
	@Override
	public void onActionStateChanged(int position, int actionState) {
		if (mAdapter.isExpanded(getFlexibleAdapterPosition())) {
			collapseView(position);
		}
		super.onActionStateChanged(position, actionState);
	}

}