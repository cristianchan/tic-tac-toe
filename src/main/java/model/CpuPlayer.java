package model;

import service.CpuPlayerService;

public class CpuPlayer extends Player {
    private final CpuPlayerService cpuPlayerService;

    public CpuPlayer(final String name, final String symbol, CpuPlayerService cpuPlayerService) {
        super(name, symbol);
        this.cpuPlayerService = cpuPlayerService;
    }

    @Override
    public Cell getCell(Game game) {
        return cpuPlayerService.getCpuCell(game);
    }


}
