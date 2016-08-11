function parseResult(result) {
    isHistogram = false;
    if (result.graphType === 'LINES') {
        return parseGraphLines(result);
    } else if (result.graphType === 'STACKED_AREA') {
        return parseGraphStackedArea(result);
    } else if (result.graphType === 'STACKED_BAR') {
        return parseGraphStackedBar(result);
    } else if (result.graphType === 'PIE') {
        return parseGraphPie(result);
    } else if (result.graphType === 'LINE_TIME') {
        return parseGraphLine(result);
    } else if (result.graphType === 'LINE') {
        return parseGraphLine(result);
    } else if (result.graphType === 'BAR') {
        return parseGraphBar(result);
    } else if (result.graphType === 'BAR_TIME') {
        return parseGraphBarTime(result);
    } else if (result.graphType === 'HISTOGRAM_AREA') {
        isHistogram = true;
        return parseGraphHistogramArea(result);
    } else if (result.graphType === 'HISTOGRAM_BAR') {
        isHistogram = true;
        return parseGraphHistogramBar(result);
    } else if (result.graphType === 'TABLE') {
        return parseGraphTable(result);
    }
}

function parseGraphBar(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy',
        type: 'column'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        },
        type: result.xAxisType
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType,
        min: 0
    };
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: false,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    };
    r.series = result.series;
    return r;
}

function parseGraphBarTime(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy',
        type: 'column'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        },
        type: result.xAxisType
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType,
        min: 0
    };
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: result.legendEnabled,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    };
    r.series = result.series;
    return r;
}

function parseGraphLine(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy',
        type: 'line'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        },
        type: result.xAxisType
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType,
        min: 0
    };
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: result.legendEnabled,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        line: {
            marker: {
                enabled: false
            }
        }
    };
    r.series = result.series;
    return r;
}

function parseGraphPie(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy',
        type: 'pie'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        },
        type: result.xAxisType
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType
    };
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: result.legendEnabled,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: false
            },
            showInLegend: true
        }
    };
    r.series = result.series;
    return r;
}

function parseGraphLines(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        },
        type: result.xAxisType
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType,
        min: 0
    };
    r.zAxis = {
        title: {
            text: result.zAxisTitle
        }
    };
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: result.legendEnabled,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        line: {
            marker: {
                enabled: false
            }
        }
    };
    r.series = result.series;
    return r;
}

function parseGraphStackedArea(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy',
        type: 'area'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        },
        type: result.xAxisType
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType,
        min: 0
    };
    r.zAxis = {
        title: {
            text: result.zAxisTitle
        }
    };    
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: result.legendEnabled,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        area: {
            stacking: 'normal',
            marker: {
                enabled: false
            }
        }
    };
    r.series = result.series;
    return r;
}

function parseGraphStackedBar(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy',
        type: 'column'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        },
        type: result.xAxisType
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType,
        min: 0
    };
    r.zAxis = {
        title: {
            text: result.zAxisTitle
        }
    };    
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: result.legendEnabled,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        column: {
            stacking: 'normal'
        }
    };
    r.series = result.series;
    return r;
}

function parseGraphHistogramArea(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy',
        type: 'area'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        }
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType
    };
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: result.legendEnabled,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        area: {
            marker: {
                enabled: true,
                symbol: 'diamond',
                radius: 3,
                states: {
                    hover: {
                        enabled: true
                    }
                }
            }
        }

    };
    r.series = result.series;
    return r;
}

function parseGraphHistogramBar(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        zoomType: 'xy',
        type: 'column'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        }
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType
    };
    r.tooltip = {
        valuePrefix: result.tooltipPrefix,
        valueSuffix: result.tooltipSuffix
    };
    r.legend = {
        enabled: result.legendEnabled,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 1
    };
    r.plotOptions = {
        column: {
            pointPadding: 0,
            borderWidth: 0,
            groupPadding: 0
        }
    };
    r.series = result.series;
    return r;
}

function parseGraphTable(result) {
    var r = {};
    r.title = {
        text: result.title
    };
    r.chart = {
        type: 'table'
    };
    r.xAxis = {
        title: {
            text: result.xAxisTitle
        },
        type: result.xAxisType
    };
    r.yAxis = {
        title: {
            text: result.yAxisTitle
        },
        type: result.yAxisType,
        min: 0
    };
    r.zAxis = {
        title: {
            text: result.zAxisTitle
        }
    };    
    r.series = result.series;
    return r;
}