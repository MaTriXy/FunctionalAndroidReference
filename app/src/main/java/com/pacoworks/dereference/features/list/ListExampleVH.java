/*
 * Copyright (c) pakoito 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pacoworks.dereference.features.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.pacoworks.dereference.widgets.ReactiveDNDTouchHelper;

public class ListExampleVH extends RecyclerView.ViewHolder implements ReactiveDNDTouchHelper.ReactiveDNDViewHolder {
    public ListExampleVH(Context context) {
        super(new TextView(context));
        TextView txv = (TextView) itemView;
        txv.setPadding(100, 100, 100, 100);
    }

    @Override
    public void onDragStarted() {
        itemView.animate().cancel();
        itemView.animate().alpha(0.4f).scaleX(2f).scaleY(2f);
    }

    @Override
    public void onDragEnded() {
        itemView.animate().cancel();
        itemView.animate().alpha(1f).scaleX(1f).scaleY(1f);
    }
}