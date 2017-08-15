package com.orbismobile.materialtoolbarselected;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author Aidan Follestad (afollestad)
 */
class NativeAdapter extends RecyclerView.Adapter<NativeAdapter.MainViewHolder> {

    interface OnSelectItemListener {
        void onItemClicked(int index);
    }

    private List<UserEntity> userEntities;
    private OnSelectItemListener onSelectItemListener;

    NativeAdapter(List<UserEntity> userEntities, OnSelectItemListener onSelectItemListener) {
        this.userEntities = userEntities;
        this.onSelectItemListener = onSelectItemListener;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_list_native, viewGroup, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder mainViewHolder, int i) {
        if (userEntities.get(i).isSelected()) {

            mainViewHolder.vCircle.setBackgroundResource(R.drawable.layer_list_checked);
            mainViewHolder.itemView.setActivated(userEntities.get(i).isSelected());
        } else {

            mainViewHolder.vCircle.setBackgroundResource(R.drawable.layer_list_unchecked);
            mainViewHolder.itemView.setActivated(userEntities.get(i).isSelected());
        }

        mainViewHolder.itemView.setTag("item:" + i);
        //mainViewHolder.icon.setTag("icon:" + i);
        mainViewHolder.lblTitle.setText(userEntities.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return userEntities.size();
    }

    public int getItemSelected() {
        int count = 0;
        for (UserEntity userEntity : userEntities) {
            if (userEntity.isSelected()) {
                count++;
            }
        }

        return count;
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView lblTitle;
        final View vCircle;
        final ImageView imgChecked;

        MainViewHolder(View itemView) {
            super(itemView);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            vCircle = itemView.findViewById(R.id.vCircle);
            imgChecked = itemView.findViewById(R.id.imgChecked);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            final AnimatorSet leftIn, rightOut;
            leftIn = (AnimatorSet) AnimatorInflater.loadAnimator(lblTitle.getContext(), R.animator.card_flip_left_in);
            rightOut = (AnimatorSet) AnimatorInflater.loadAnimator(lblTitle.getContext(), R.animator.card_flip_right_out);

            final AnimatorSet showFrontAnim = new AnimatorSet();
            final AnimatorSet showBackAnim = new AnimatorSet();

            leftIn.setTarget(vCircle);
            showFrontAnim.play(leftIn);
            showFrontAnim.start();

            showFrontAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (userEntities.get(getAdapterPosition()).isSelected()) {
                        itemView.setActivated(false);
                        userEntities.get(getAdapterPosition()).setSelected(false);
                        vCircle.setBackgroundResource(R.drawable.layer_list_unchecked);
                    } else {
                        itemView.setActivated(true);
                        vCircle.setBackgroundResource(R.drawable.layer_list_checked);
                        userEntities.get(getAdapterPosition()).setSelected(true);
                    }

                    rightOut.setTarget(vCircle);
                    showBackAnim.play(rightOut);
                    showBackAnim.start();

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }
}
