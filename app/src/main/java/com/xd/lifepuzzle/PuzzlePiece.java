package com.xd.lifepuzzle;

import android.content.Context;

public class PuzzlePiece extends androidx.appcompat.widget.AppCompatImageView {
    public int xCoordinate;
    public int yCoordinate;
    public int pieceWidth;
    public int pieceHeight;
    public boolean canMove = true;

    public PuzzlePiece(Context context) {
        super(context);
    }
}