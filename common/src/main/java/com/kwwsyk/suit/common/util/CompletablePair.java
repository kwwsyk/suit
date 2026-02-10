package com.kwwsyk.suit.common.util;

import it.unimi.dsi.fastutil.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;

public class CompletablePair<L, R> implements Pair<L, R> {
    @Nullable
    private L left;
    @Nullable
    private R right;
    @Nullable
    private final BiConsumer<L, R> onCompleteFunc;
    private boolean isCompleted = false;

    protected CompletablePair(@Nullable L left, @Nullable R right, @Nullable BiConsumer<L, R> onCompleteFunc){
        this.left = left;
        this.right = right;
        this.onCompleteFunc = onCompleteFunc;
    }

    protected CompletablePair(@Nullable L left, @Nullable R right){
        this(left, right, null);
    }

    public static <L, R> CompletablePair<L, R> ofLeft(L left){
        Objects.requireNonNull(left);
        return new CompletablePair<>(left, null);
    }

    public static <L, R> CompletablePair<L, R> ofRight(R right){
        Objects.requireNonNull(right);
        return new CompletablePair<>(null, right);
    }

    public static <L, R> CompletablePair<L, R> ofLeft(L left, BiConsumer<L, R> onCompleteFunc){
        Objects.requireNonNull(left);
        return new CompletablePair<>(left, null, onCompleteFunc);
    }

    public static <L, R> CompletablePair<L, R> ofRight(R right, BiConsumer<L, R> onCompleteFunc){
        Objects.requireNonNull(right);
        return new CompletablePair<>(null, right, onCompleteFunc);
    }

    @SuppressWarnings("unchecked")
    public void tryComplete(Object remain){
        try {
            if(left == null){
                left = (L)remain;
            } else if( right == null){
                right = (R)remain;
            }
            onComplete();
        } catch (Exception e) {
            throw new IllegalArgumentException("Exception thrown possibly due to wrong param: ",e);
        }
    }

    public void completeLeft(L left){
        if(this.left != null || right == null){
            throw new IllegalStateException("Try to complete a pair who has left or has not right with left param.");
        }
        this.left = left;
        onComplete();
    }

    public void completeRight(R right){
        if(this.right != null || left == null){
            throw new IllegalStateException("Try to complete a pair who has right or has not left with right param");
        }
        this.right = right;
        onComplete();
    }

    public void completeLeftSoft(L left){
        this.left = left;
        if(right == null) return;
        onComplete();
    }

    public void completeRightSoft(R right){
        this.right = right;
        if(left == null) return;
        onComplete();
    }

    public void onComplete(){
        if(onCompleteFunc != null) onCompleteFunc.accept(left, right);
        isCompleted = true;
    }

    public boolean isCompleted(){
        return isCompleted;
    }

    /**
     * Returns the left element of this pair.
     *
     * @return the left element of this pair.
     */
    @Override@Nullable
    public L left() {
        return left;
    }

    /**
     * Returns the right element of this pair.
     *
     * @return the right element of this pair.
     */
    @Override@Nullable
    public R right() {
        return right;
    }
}
