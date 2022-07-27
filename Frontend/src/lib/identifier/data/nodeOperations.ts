import { Node, nodes, getIdentifierStore } from "./identifierStore";

export type NodeCount = {
    startNodeCount: number,
    normalNodeCount: number,
    endNodeCount: number
}

export function countNodeTypes(): NodeCount {

    const nodes = getIdentifierStore();
    let startCount: number = 0;
    let normalCount: number = 0;
    let endCount: number = 0;

    nodes.forEach(element => {
        switch (element.type) {
            case "start":
                startCount++;
                break;

            case "normal":
                normalCount++;
                break;

            case "end":
                endCount++;
                break;            
        }
    });

    const nodeCountData: NodeCount = {
        startNodeCount: startCount,
        normalNodeCount: normalCount,
        endNodeCount: endCount        
    }

    return nodeCountData;

}

export function deleteNode(node: Node) {

    const nodeArray = getIdentifierStore();
    let index;

    for (let i = 0; i < nodeArray.length; i++) {
        if (node.id == nodeArray[i].id) {
            index = i;
        } else {
            if (nodeArray[i].oneGoal && nodeArray[i].goal == node.id) {
                nodeArray[i].goal = undefined;
            } else {
                nodeArray[i].options.forEach(element => {
                    if (element.goal == node.id) {
                        element.goal = undefined;
                    }
                });
            }
        }
    }

    if (index != undefined) {
        nodeArray.splice(index, 1);
        nodes.set(Array(0));
        nodes.set(nodeArray);
    }    

}