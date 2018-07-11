#lang racket
(require srfi/13)

;; Lista numero -> Lógico
;; ---------------------
;; verifica se a insercao em uma linha eh segura
(define (linha-segura? lista nr)
(cond
    [(empty? lista) #t]
    [(equal? (first lista) nr) #f]
    [else
(linha-segura? (rest lista) nr)]))


;; Lista numero -> Lógico
;; ---------------------
;; verifica se a insercao em uma diagonal eh segura
(define (diagonal-segura? lista nr)
  (let ([size (length lista)])
  (define (diagonal-segura-body? lista nr acc)
    (cond
      [(empty? lista) #t]
      [(equal? (abs(- (first lista) nr)) (abs(- acc size))) #f]
      [else
       (diagonal-segura-body? (rest lista) nr (add1 acc))]))
(diagonal-segura-body? lista nr 0)))

;; Lista numero -> Lógico
;; ---------------------
;; implementa a verificacao geral se pode inserir um elemento no tabuleiro
(define (seguro-inserir? lista nr)
  (and (diagonal-segura? lista nr) (linha-segura? lista nr)))

;; Lista numero -> Numero
;; ---------------------
;; encontra o primeiro que ainda pode aumentar
(define (ultimo-visitado-idx lista tamanho)
  (cond
    [(< (last lista) (- tamanho 1)) (index-of lista (last lista))]
    [else (ultimo-visitado-idx (take lista (-(length lista) 1)) tamanho)]))

;; Lista numero -> Numero
;; ---------------------
;; encontra o primeiro que ainda pode aumentar
(define (ultimo-visitado-value lista tamanho)
  (cond
    [(< (last lista) (- tamanho 1)) (last lista)]
    [else (ultimo-visitado-value (take lista (-(length lista) 1)) tamanho)]))

;; numero -> Lista
;; ---------------------
;; resolve o N-rainhas
(define (n-rainhas tamanho)
  (define (n-rainhas-body lista nr tamanho)
  (cond
    [(equal?(length lista) tamanho) (map add1 lista)]
    [(seguro-inserir? lista nr) (n-rainhas-body (append lista (list nr)) 0 tamanho)]
    [(< nr (- tamanho 1)) (n-rainhas-body lista (add1 nr) tamanho)]
    [(equal? nr (- tamanho 1)) (n-rainhas-body (take lista (ultimo-visitado-idx lista tamanho)) (add1 (ultimo-visitado-value lista tamanho)) tamanho)])
    )
  (n-rainhas-body '() 0 tamanho))