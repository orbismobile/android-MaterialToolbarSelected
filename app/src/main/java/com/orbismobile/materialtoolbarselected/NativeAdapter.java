package com.orbismobile.materialtoolbarselected;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author Carlos Leonardo Camilo Vargas Huamán
 * @alias CarlitosDroid
 */
class NativeAdapter extends RecyclerView.Adapter<NativeAdapter.MainViewHolder> {

    interface OnSelectItemListener {
        void onItemClicked(int position);
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
            mainViewHolder.llContainer.setBackgroundResource(R.color.md_grey_200);
            mainViewHolder.imgUnchecked.setBackgroundResource(R.drawable.layer_list_checked);
            mainViewHolder.imgUnchecked.setImageResource(0);
            mainViewHolder.imgChecked.setVisibility(View.VISIBLE);
        } else {
            mainViewHolder.llContainer.setBackgroundResource(R.color.md_white_1000);
            mainViewHolder.imgUnchecked.setBackgroundResource(R.drawable.layer_list_unchecked);
            mainViewHolder.imgUnchecked.setImageResource(R.drawable.ic_home_white_48dp);
            mainViewHolder.imgChecked.setVisibility(View.INVISIBLE);
        }

        mainViewHolder.lblTitle.setText(userEntities.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return userEntities.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final LinearLayout llContainer;
        final LinearLayout llMessage;
        final TextView lblTitle;
        final ImageView imgChecked;
        final ImageView imgUnchecked;

        MainViewHolder(View itemView) {
            super(itemView);
            llContainer = itemView.findViewById(R.id.llContainer);
            llMessage = itemView.findViewById(R.id.llMessage);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            imgChecked = itemView.findViewById(R.id.imgChecked);
            imgUnchecked = itemView.findViewById(R.id.imgUnchecked);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            final AnimatorSet leftIn, rightOut;
            leftIn = (AnimatorSet) AnimatorInflater.loadAnimator(lblTitle.getContext(), R.animator.card_flip_left_in);
            rightOut = (AnimatorSet) AnimatorInflater.loadAnimator(lblTitle.getContext(), R.animator.card_flip_right_out);

            final AnimatorSet showFrontAnim = new AnimatorSet();
            final AnimatorSet showBackAnim = new AnimatorSet();

            leftIn.setTarget(imgUnchecked);
            showFrontAnim.play(leftIn);
            showFrontAnim.start();

            showFrontAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (userEntities.get(getAdapterPosition()).isSelected()) {
                        llContainer.setBackgroundResource(R.color.md_white_1000);
                        userEntities.get(getAdapterPosition()).setSelected(false);
                        imgUnchecked.setBackgroundResource(R.drawable.layer_list_unchecked);
                        imgUnchecked.setImageResource(R.drawable.ic_home_white_48dp);
                        imgChecked.setVisibility(View.INVISIBLE);
                    } else {
                        llContainer.setBackgroundResource(R.color.md_grey_200);
                        userEntities.get(getAdapterPosition()).setSelected(true);
                        imgUnchecked.setBackgroundResource(R.drawable.layer_list_checked);
                        imgUnchecked.setImageResource(0);
                        imgChecked.setVisibility(View.VISIBLE);
                    }

                    rightOut.setTarget(imgUnchecked);
                    showBackAnim.play(rightOut);
                    showBackAnim.start();
                    onSelectItemListener.onItemClicked(getAdapterPosition());
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

    void clearAllSelections() {
        for (UserEntity userEntity : userEntities) {
            userEntity.setSelected(false);
        }
        notifyDataSetChanged();
    }

    int getSelectedItemCount() {
        int count = 0;
        for (UserEntity userEntity : userEntities) {
            if (userEntity.isSelected()) {
                count++;
            }
        }
        return count;
    }
}
