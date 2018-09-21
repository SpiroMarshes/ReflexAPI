/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.tools;

import me.parozzz.reflex.utilities.Util;

/**
 * @author Paros
 */
public final class Cooldown
{
    private final long cooldownTime;
    private long timeStamp;

    public Cooldown(long cooldownTime)
    {
        this.cooldownTime = cooldownTime;
        resetCooldown();
    }

    public final long getCooldownTime()
    {
        return cooldownTime;
    }

    public final void resetCooldown()
    {
        timeStamp = System.currentTimeMillis();
    }

    public final long getRemainingTime()
    {
        return cooldownTime - (System.currentTimeMillis() - timeStamp);
    }

    public final boolean stillWaiting()
    {
        return System.currentTimeMillis() - timeStamp < cooldownTime;
    }

    public final String toTime()
    {
        return Util.longToTime(getRemainingTime());
    }
}
