
<script src = "/assets/admin/js/orders.js"> </script>

<div class="modal fade" id="orderModal" tabindex="-1" aria-labelledby="orderModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="orderModalLabel">Invia</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <label class="col-form-label">Inserisci Codice Spedizione</label>
                <input id = "shipmentNumberInput" type="text" class="form-control">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button id = "saveShipmentNumber" onclick="updateOrderStatus()" value = "" type="button" class="btn btn-success">Salva</button>
            </div>
        </div>
    </div>
</div>